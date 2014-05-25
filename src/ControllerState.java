
/**
 * Represents a single controller state in MK64 (which buttons are being pressed and the position of the
 * analog stick).
 */
class ControllerState {
	private final boolean a; // is A button being pressed
	private final boolean b;
	private final boolean z;
	private final boolean r;
	
	private final int x; // x position of the analog stick (-127 to 127)
	private final int y;
	
	public ControllerState(boolean a, boolean b, boolean z, boolean r, int x, int y) {
		this.a = a;
		this.b = b;
		this.z = z;
		this.r = r;
		this.x = x;
		this.y = y;
	}
	
	public String toBizHawkString() {
		StringBuilder str = new StringBuilder();
		// UDRLBAZSLRudlr xxx, yyy
		str.append("|.|...."); // UDRL
		str.append(b ? "B" : "."); // B
		str.append(a ? "A" : "."); // A
		str.append(z ? "Z" : "."); // Z
		str.append(".."); // Start , L
		str.append(r ? "R" : "."); // R
		str.append("...."); // c buttons;
		str.append( x<0 ? "-" : " ");
		str.append(String.format("%03d", Math.abs(x)));
		str.append(",");
		str.append( y<0 ? "-" : " ");
		str.append(String.format("%03d", Math.abs(y)));
		str.append( "|.............. 000, 000|.............. 000, 000|.............. 000, 000|"); // other players
		return str.toString();
	}
	
	public String toTabDelimitedString() {
		StringBuilder builder = new StringBuilder();
		if (a) builder.append("A");
		if (b) builder.append("B");
		if (z) builder.append("Z");
		if (r) builder.append("R");
		builder.append("\t");
		builder.append(x);
		builder.append("\t");
		builder.append(y);
		return builder.toString();
	}

	public boolean isA() {
		return a;
	}

	public boolean isB() {
		return b;
	}

	public boolean isZ() {
		return z;
	}

	public boolean isR() {
		return r;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	
}