function ternary ( cond , T , F )
    if cond then return T else return F end
end

function table.val_to_str ( v )
  if "string" == type( v ) then
    v = string.gsub( v, "\n", "\\n" )
    if string.match( string.gsub(v,"[^'\"]",""), '^"+$' ) then
      return "'" .. v .. "'"
    end
    return '"' .. string.gsub(v,'"', '\\"' ) .. '"'
  else
    return "table" == type( v ) and table.tostring( v ) or
      tostring( v )
  end
end

function table.key_to_str ( k )
  if "string" == type( k ) and string.match( k, "^[_%a][_%a%d]*$" ) then
    return k
  else
    return "[" .. table.val_to_str( k ) .. "]"
  end
end

function table.tostring( tbl )
  local result, done = {}, {}
  for k, v in ipairs( tbl ) do
    table.insert( result, table.val_to_str( v ) )
    done[ k ] = true
  end
  for k, v in pairs( tbl ) do
    if not done[ k ] then
      table.insert( result,
        table.key_to_str( k ) .. "=" .. table.val_to_str( v ) )
    end
  end
  return "{" .. table.concat( result, "," ) .. "}"
end

local m64_filename = forms.openfile(nil,nil,"Mupen Movie Files (*.M64)|*.M64|All Files (*.*)|*.*")

console.clear()
if m64_filename == "" then
  console.log("No movie selected. Exiting.")
  return
end

console.log("Opening movie for playback: " .. m64_filename)

-- Open the file and read past the header data
local input_file = assert(io.open(m64_filename, "rb"))

-- Flag to note that we've reached the end of the movie
local finished = false

-- Since m64 movies do not record on lag frames, we need to know if the input was actually used for the current frame
local input_was_used = false
function input_used()
	if not finished then
   input_was_used = true
  end
end
event.oninputpoll(input_used)

local inputCount = 0

local buttons = { }
local X
local Y
-- Reads in the next frame of data from the movie, or sets the finished flag if no frames are left
function read_next_frame()
  local dataLine = input_file:read()
  if not dataLine then 
    console.log("dataLine was empty")
    finished = true
    return
  end
  
  -- Flag	 UDRLBAZSLRudlr xxx, yyy	 other controllers
  buttons["P1 DPad U"] = ternary(string.sub(dataLine,4,4) == "U",true,nil)
  buttons["P1 DPad D"] = ternary(string.sub(dataLine,5,5) == "D",true,nil)
  buttons["P1 DPad R"] = ternary(string.sub(dataLine,6,6) == "R",true,nil)
  buttons["P1 DPad L"] = ternary(string.sub(dataLine,7,7) == "L",true,nil)
  buttons["P1 B"] = ternary(string.sub(dataLine,8,8) == "B",true,nil)
  buttons["P1 A"] = ternary(string.sub(dataLine,9,9) == "A",true,nil)
  buttons["P1 Z"] = ternary(string.sub(dataLine,10,10) == "Z",true,nil)
  buttons["P1 Start"] = ternary(string.sub(dataLine,11,11) == "S",true,nil)
  buttons["P1 L"] = nil
  buttons["P1 R"] = ternary(string.sub(dataLine,13,13) == "R",true,nil)
  buttons["P1 C Right"] = nil
  buttons["P1 C Down"] = nil
  buttons["P1 C Up"] = nil
  buttons["P1 C Left"] = nil
  
  X = tonumber(string.sub(dataLine,18,21))
  Y = tonumber(string.sub(dataLine,23,26))
  
  inputCount = inputCount + 1
  -- console.log(string.format('Processed %i frames', inputCount))
   
end

memory.usememorydomain("RDRAM")
local p1x = 0
local p1y = 0
local raceTimer = 0
local outputFile = assert(io.open("testOutput.csv", "w"))

while true do
  -- console.log(string.format('Current Buttons : %s', table.tostring(joypad.get())))

  -- Only read the next frame of data if the last one was used
  if input_was_used and not finished then
    read_next_frame()
	
	p1x = mainmemory.readfloat(0x0F69A4, false)
	p1y = mainmemory.readfloat(0x0F69AC, false)
	raceTimer = mainmemory.read_u32_le(0x18CA7C)
	console.log(string.format("%d,%f,%f\n",raceTimer,p1x,p1y))
	outputFile:write(string.format("%d,%f,%f\n",raceTimer,p1x,p1y)) 
	
	-- console.log(string.format('Buttons : %s', table.tostring(buttons)))
    input_was_used = false
  end
  
  if not finished then
    joypad.set(buttons)
    local analogs = { ["P1 X Axis"] = X, ["P1 Y Axis"] = Y }
    joypad.setanalog(analogs)
  end
  
  if finished then
    console.log("Movie finished")
    client.pause()
	outputFile:close()
    return
  end
  
  emu.frameadvance()
end


 
