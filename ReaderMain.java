import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.*;

import javax.swing.*;


public class ReaderMain extends JFrame implements ActionListener{
	
	private JComboBox<String> regions;
	private ArrayList<JButton> buttons;
private String[] linkNames={"britishcolumbia","calgary","edmonton","saskatchewan","saskatoon","manitoba","thunderbay","sudbury","windsor","kitchenerwaterloo",
							"toronto","ottawa","montreal","newbrunswick","pei","novascotia","newfoundland","north"};
	private String[] names={"British Columbia","Calgary","Edmonton","Saskatchewan","Saskatoon","Manitoba","Thunder Bay","Sudbury","Windsor","Kitchener-Waterloo",
							"Toronto","Ottawa","Montreal","New Brunswick","Prince Edward Island","Nova Scotia","Newfoundland and Labrador","North"};
	private JButton back, back2, select, getStory;
	private JPanel selectRegion, article, choosing, titlePanel, titlePanel2, main, main2;
	private JLabel title, author, story, instruct, programTitle, pt2;
	private Title cbc;
	private String html1="<html><body style='width: ",html2="px'>", titleString="CBC News: ", link;
	
	public static void main(String[] args) {
		ReaderMain window=new ReaderMain();
	}
	
	public ReaderMain(){
		setLayout(new FlowLayout());
		programTitle=new JLabel();
		programTitle.setFont(new Font("Aharoni",Font.PLAIN, 25));
		pt2=new JLabel();
		pt2.setFont(new Font("Aharoni",Font.PLAIN, 25));
		
		regions=new JComboBox<String>(names);
		regions.setSelectedIndex(0);
		
		back=new JButton("Back");
		back.addActionListener(this);
		back2=new JButton("Back");
		back2.addActionListener(this);
		select=new JButton("Select");
		select.addActionListener(this);
		getStory=new JButton("Read Full Story");
		getStory.addActionListener(this);
		
		title=new JLabel("");
		author=new JLabel("");
		story=new JLabel("");
		instruct=new JLabel("Select a Region: ");
		instruct.setFont(new Font("Aharoni",Font.PLAIN, 25));

		buttons=new ArrayList<JButton>();
		
		main=new JPanel(new BorderLayout());
		main2=new JPanel(new BorderLayout());
		
		selectRegion=new JPanel(new FlowLayout());
		selectRegion.add(instruct);
		selectRegion.add(regions);
		selectRegion.add(select);
		selectRegion.setVisible(true);
		//selectRegion.setPreferredSize(new Dimension(450, 450));
		
		choosing=new JPanel(new GridLayout(0,2));
		choosing.setVisible(false);
		main.add(choosing, BorderLayout.CENTER);
		
		article=new JPanel(new GridLayout(4,0));
		article.add(title);
		article.add(author);
		article.add(story);
		article.add(getStory);
		article.setVisible(false);
		main2.add(article, BorderLayout.CENTER);
		
		titlePanel=new JPanel(new FlowLayout());
		titlePanel.add(back);
		titlePanel.add(programTitle);
		titlePanel.setVisible(false);
		
		titlePanel2=new JPanel(new FlowLayout());
		titlePanel2.add(back2);
		titlePanel2.add(pt2);
		titlePanel2.setVisible(false);
		
		main.add(titlePanel, BorderLayout.NORTH);
		main2.add(titlePanel2, BorderLayout.NORTH);
		
		main.setBackground(Color.DARK_GRAY);
		setTitle("CBC RSS Reader");
		setSize(400,125);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setContentPane(selectRegion);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==select){
			cbc=new Title(linkNames[regions.getSelectedIndex()]);
			programTitle.setText(titleString+names[regions.getSelectedIndex()]);
			pt2.setText(titleString+names[regions.getSelectedIndex()]);
			selectRegion.setVisible(false);
			choosing.setVisible(true);			
			titlePanel.setVisible(true);
			
			for(int i=0;i<cbc.getTitles().size();i++){
				buttons.add(new JButton(cbc.getTitles().get(i)));
				choosing.add(buttons.get(i));
				buttons.get(i).setContentAreaFilled(false);
				buttons.get(i).addActionListener(this);
			}
			setContentPane(main);
			setSize(800,800);
			setLocationRelativeTo(null);
		}
		
		for(int i=0;i<buttons.size();i++){
			if(e.getSource()==buttons.get(i)){
				choosing.setVisible(false);
				article.setVisible(true);
				titlePanel2.setVisible(true);
				title.setText(cbc.getTitles().get(i));
				if(cbc.getAuthors().get(i).equals("")){
					author.setText("");
				}else{
					author.setText("Author: "+cbc.getAuthors().get(i));
				}
				story.setText(html1+300+html2+cbc.getStories().get(i));
				link=cbc.getStoryLink().get(i);
				setContentPane(main2);
				setSize(600,600);
				setLocationRelativeTo(null);
			}
		}
		
		if(e.getSource()==back){
			for(int i=0;i<cbc.getTitles().size();i++){
				choosing.remove(buttons.get(i));
			}
			buttons.clear();
			choosing.setVisible(false);
			article.setVisible(false);
			selectRegion.setVisible(true);
			titlePanel.setVisible(false);
			setContentPane(selectRegion);
			setSize(400,125);
			setLocationRelativeTo(null);
			
		}
		
		if(e.getSource()==back2){
			choosing.setVisible(true);
			titlePanel.setVisible(true);
			titlePanel2.setVisible(false);
			article.setVisible(false);
			selectRegion.setVisible(false);
			setContentPane(main);
			setSize(800,800);
			setLocationRelativeTo(null);
			
		}
		
		if(e.getSource()==getStory){
			try {
			    Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (Exception x) {}
		}

	}
}
