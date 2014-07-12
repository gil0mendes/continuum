package com.continuum;

/**
 * Created by gil0mendes on 11/07/14.
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class DebugWindow extends javax.swing.JFrame {

	float noise[][];

	/** Creates new form DebugWindow */
	public DebugWindow() {
		initComponents();

		PerlinNoiseGenerator pGen = new PerlinNoiseGenerator();
		noise = new float[512][512];


		//for (int x=0; x<512; x++) {
		//	for (int y=0; y<512; y++) {
		//		noise[x][y] = pGen.getTerrainHeightAt(x, y);
		//	}
		//}
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Debug Window");
		setAlwaysOnTop(true);
		setSize(new java.awt.Dimension(256, 256));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 365, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(0, 297, Short.MAX_VALUE)
		);

		pack();
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, 512, 512);


		for (int x = 0; x < 512; x++) {
			for (int z = 0; z < 512; z++) {
				float grayValue = noise[x][z];

				if (grayValue < 0)
					grayValue = 0;

				g.setColor(new Color(grayValue, grayValue, grayValue));
				g.drawRect(x, z, 1, 1);
			}
		}
	}
}