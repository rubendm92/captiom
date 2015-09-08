package captiom.core.model.device.dimension;

public class Dimension {

	private final Width width;
	private final Height height;

	public Dimension(Width width, Height height) {
		this.width = width;
		this.height = height;
	}

	public Width width() {
		return width;
	}

	public Height height() {
		return height;
	}
}
