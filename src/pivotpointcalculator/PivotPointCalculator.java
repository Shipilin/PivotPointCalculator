/*
 * Calculating pivot point for POLARIS
 * version 1: 24.03.2018
 * Mikhail Shipilin
 */
package pivotpointcalculator;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Shipilin
 */
public class PivotPointCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new PivotPointCalculator();
    }

    public PivotPointCalculator(){
        
    JFrame guiFrame = new JFrame();

    //make sure the program exits when the frame closes
    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.setTitle("Pivot point calculator");
    guiFrame.setSize(400,300);

    //This will center the JFrame in the middle of the screen
    guiFrame.setLocationRelativeTo(null);
    guiFrame.setLayout(new GridLayout(3,1));

    //Panels for GUI
    JPanel inputArea = new JPanel(new GridLayout(3, 4));
    inputArea.setSize(400, 150);
    JPanel buttonArea = new JPanel(new GridLayout(2,1));
    buttonArea.setSize(400, 50);
    JPanel outputArea = new JPanel(new GridLayout(2, 4));
    outputArea.setSize(400, 100);
    
    //Create four user input fields
    JTextField txtInput[] = new JTextField[]{
        new JTextField("0.0"),
        new JTextField("0.0"),
        new JTextField("0.0"),
        new JTextField("0.0"),
    };
    
    //Create four user output fields fields
    JTextField txtOutput[] = new JTextField[]{
        new JTextField(),
        new JTextField(),
        new JTextField(),
        new JTextField(),
    };
    
    //Create four user input names fields
    JLabel[] labelInput = new JLabel[]{
        new JLabel("x1", SwingConstants.RIGHT),
        new JLabel("x2", SwingConstants.RIGHT),
        new JLabel("y1", SwingConstants.RIGHT),
        new JLabel("y2", SwingConstants.RIGHT),
    };
    
    //Create four user output names fields
    JLabel[] labelOutput = new JLabel[]{
        new JLabel("x1", SwingConstants.RIGHT),
        new JLabel("x2", SwingConstants.RIGHT),
        new JLabel("y1", SwingConstants.RIGHT),
        new JLabel("y2", SwingConstants.RIGHT),
    };
    
    //Angle input
    JLabel labelAngle = new JLabel("Angle (deg)", SwingConstants.RIGHT);
    JTextField angle = new JTextField("0.0");
    
    //Adding labels and input/output fields to the panels with indexes
    for (int i = 0; i < txtInput.length; i++){
        inputArea.add(labelInput[i]);
        outputArea.add(labelOutput[i]);
        inputArea.add(txtInput[i]);
        outputArea.add(txtOutput[i]);
    } 
    
    //Adding Angle and empty label
    inputArea.add(labelAngle);
    inputArea.add(angle);
    inputArea.add(new JLabel());
    inputArea.add(new JLabel());

    
    //Explanations
    JLabel text = new JLabel("Two possible circles have centers at", SwingConstants.CENTER);
    //Adding button
    JButton calculate = new JButton("Calculate");
    buttonArea.add(text);
    buttonArea.add(calculate);
  
    //The JFrame uses the BorderLayout layout manager.
    //Put the two JPanels and JButton in different areas.
    guiFrame.add(inputArea, BorderLayout.PAGE_START);
    guiFrame.add(buttonArea, BorderLayout.CENTER);
    guiFrame.add(outputArea, BorderLayout.PAGE_END);
    
    //make sure the JFrame is visible
    guiFrame.setVisible(true);
    
    //The ActionListener class is used to handle the
    //event that happens when the user clicks the button.
    //As there is not a lot that needs to happen we can 
    //define an anonymous inner class to make the code simpler.
    calculate.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            //receive input from text fields
            double x1 = Double.parseDouble(txtInput[0].getText());
            double x2 = Double.parseDouble(txtInput[1].getText());
            double y1 = Double.parseDouble(txtInput[2].getText());
            double y2 = Double.parseDouble(txtInput[3].getText());
            double ang = Double.parseDouble(angle.getText());
            
            //Distance between two known points
            double d = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
            //Radius of the pivot circle
            double r = d/(2*Math.sin(ang/2));            
            
            double e,f;
            double x0,y0;
            double a,b;
            double x3=0,y3=0,x4=0,y4=0;
            
            //if second point is on the right from first
            if(x2 >= x1){
                e = (x2-x1)/2;
                x0 = x1 + e;
            }
            else{
                e = (x1-x2)/2;
                x0 = x2 + e;
            }
            //if second point is higher than first
            if(y2 >= y1){
                f = (y2-y1)/2;
                y0 = y1 + f;
            }
            else{
                f = (y1-y2)/2;
                y0 = y2 + f;
            }
            
            //Mane diagonals of the romb
            a = Math.sqrt(e*e + f*f);
            b = Math.sqrt(r*r + a*a);
            
            if((x2 >= x1) && (y2 >= y1)){
                x3 = x0 + b*f/a;
                y3 = y0 - b*e/a;
                x4 = x0 - b*f/a;
                y4 = y0 + b*e/a;
            }
            if((x2 < x1) && (y2 >= y1)){
                x3 = x0 + b*f/a;
                y3 = y0 + b*e/a;
                x4 = x0 - b*f/a;
                y4 = y0 - b*e/a;
            }
            if((x2 >= x1) && (y2 < y1)){
                x3 = x0 + b*f/a;
                y3 = y0 + b*e/a;
                x4 = x0 - b*f/a;
                y4 = y0 - b*e/a;
            }
            if((x2 < x1) && (y2 < y1)){
                x3 = x0 + b*f/a;
                y3 = y0 - b*e/a;
                x4 = x0 - b*f/a;
                y4 = y0 + b*e/a;
            }
            
            txtOutput[0].setText(String.format("%.4f", x3));
            txtOutput[1].setText(String.format("%.4f", x4));
            txtOutput[2].setText(String.format("%.4f", y3));
            txtOutput[3].setText(String.format("%.4f", y4));           
        }
    });
    
    }
}