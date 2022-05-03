package gui;
import java.awt.*;
import javax.swing.*;

public class Colors {
	
	Color background;
	Color border;
	
	public Colors() {
		this.background = new Color(221, 238, 228);
		this.border = new Color(46, 158, 130);
	}

	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
	
	

}
