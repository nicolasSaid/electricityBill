/*Nicolas Said
 *10/4/2019
 *Electric Bill
 *Mr Nickels
*/
 
import java.awt.*;
import java.applet.*;
import javax.swing.JOptionPane;
import java.text.NumberFormat;

public class Electric extends Applet {
	private String[] name;
	private String[] code;
	private int[] hours;
	private int[] heightOfBar;
	private double[] price;
	private NumberFormat money;
	private final int spacingBetween = 30; //between graphs
	private final int width = 50; //width of graph
	private final int sideOffset = 100; //offset from side
	private final int maxWidth = 600; //max x
	private final int maxLength = 300; //max y
	private final double bDiscount = .88; //business discount percent
	private final double pDiscount = .95; //private discount percent
	public void init() {
		money = NumberFormat.getCurrencyInstance(); //formating for currency
		price = new double[5];
		name = new String[5];
		code = new String[5];
		hours = new int[5];
		heightOfBar = new int[5];
		for(int i = 0; i < 5; i++){ //for loop to gain the values of each person and calculate their cost
			name[i] = JOptionPane.showInputDialog("Enter the name for person " + (i+1));
			code[i] = JOptionPane.showInputDialog("Enter the code for person " + (i+1));
			while(!(code[i].equals("p")) && !(code[i].equals("b"))){
				JOptionPane.showMessageDialog(null, "Your input is invalid" , "Error", JOptionPane.INFORMATION_MESSAGE);
					code[i] = JOptionPane.showInputDialog("Enter the code for person " + (i + 1));
			}
			hours[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter the hours for person " + (i+1)));
			final double PFIRST250 = 0.06085;
			final double PAFTER250 = 0.06421;
			final double BFIRST1000 = 0.05893;
			final double BAFTER1000 = 0.06453;
			final double PMONTH = 10.82;
			final double BMONTH = 25.00;
			final double PGOV = 5.59;
			final double BGOV = 15.29;
			if(code[i].equals("p")){
				if(hours[i] <= 250){
					
					price[i] = PMONTH + (hours[i] * PFIRST250) + PGOV;
				}
				else{
					price[i] = PMONTH + (250 * PFIRST250) + ((hours[i] - 250)*PAFTER250) + PGOV;
				}
				if(hours[i] > 2000){
					price[i] = price[i] * pDiscount;
				}
			}
			else {
				if(hours[i] <= 1000){
					price[i] = BMONTH + (hours[i] * BFIRST1000) + BGOV;
				}
				else{
					price[i] = BMONTH + (1000 * BFIRST1000) + ((hours[i] - 1000)*BAFTER1000)+BGOV;
				}
				if(hours[i] > 4000 && hours[i] < 7000){
					price[i] = price[i] * bDiscount;
				}
			}
		JOptionPane.showMessageDialog(null, "" + name[i] + ", " + code[i] + ", " + hours[i] + ", " + money.format(price[i]) , "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		int maxKWH = hours[0];
		for(int j = 1; j < 5; j++){ //finding max KWH
			if(maxKWH< hours[j])
				maxKWH = hours[j];
		}
		for(int x = 0; x < 5; x++){ //setting the size of each bar graph
			heightOfBar[x] = (int)(((maxLength - (2*sideOffset)) - 10) * ((double)hours[x] / maxKWH));
		}
	}
				
	
		

	public void paint(Graphics g) {
		g.drawLine(sideOffset,sideOffset, sideOffset, maxLength - sideOffset); //x and y axis and increments on y axis
		g.drawLine(sideOffset - 10,sideOffset, sideOffset + 10,sideOffset);
		g.drawString("6000", ((3*sideOffset) / 4), sideOffset);
		g.drawLine(sideOffset - 10,sideOffset + ((maxLength - (2*sideOffset))/4), sideOffset + 10,sideOffset + ((maxLength - (2*sideOffset))/4));
		g.drawString("4500", ((3*sideOffset) / 4), sideOffset + ((maxLength - (2*sideOffset))/4));
		g.drawLine(sideOffset - 10,sideOffset + ((maxLength - (2*sideOffset))/2), sideOffset + 10,sideOffset + ((maxLength - (2*sideOffset))/2));
		g.drawString("3000", ((3*sideOffset) / 4), sideOffset + ((maxLength - (2*sideOffset))/2));
		g.drawLine(sideOffset - 10,sideOffset + ((3*(maxLength - (2*sideOffset)))/4), sideOffset + 10,sideOffset + ((3*(maxLength - (2*sideOffset)))/4));
		g.drawString("1500", ((3*sideOffset) / 4), sideOffset + ((3*(maxLength - (2*sideOffset)))/4));
		g.drawLine(sideOffset,maxLength - sideOffset, maxWidth - sideOffset, maxLength - sideOffset);
		g.drawString("KWH", (sideOffset / 4), (maxLength / 2)); //labling
		g.drawString("People",(maxLength / 2) + sideOffset, (maxLength - (sideOffset / 4)));
		g.drawString("KWH per Person",(maxLength/2 + sideOffset),(sideOffset/2));
		
		for(int i = 0; i <5; i++){ //sets color and draws bar, name, and outputs KWH and cost
			if(code[i].equals("p"))
				g.setColor(Color.green);
			else
				g.setColor(Color.blue);
			g.fillRect(sideOffset + spacingBetween + (i * spacingBetween) + (i * width), (maxLength - sideOffset) - heightOfBar[i], width, heightOfBar[i]);
			g.setColor(Color.black);
			g.drawString(name[i],sideOffset + spacingBetween + (i * spacingBetween) + (i * width),(maxLength - ((3*sideOffset) / 4)));
			g.drawString(hours[i] + "KWH",sideOffset + spacingBetween + (i * spacingBetween) + (i * width),(maxLength - (sideOffset / 2)));
			g.drawString(money.format(price[i]),sideOffset + spacingBetween + (i * spacingBetween) + (i * width),(maxLength - sideOffset) - heightOfBar[i] - spacingBetween);
			
		}	
	}
}