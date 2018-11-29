package FileChooserTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;  
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;  
import java.beans.PropertyChangeListener;  
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;  
import javax.swing.Icon;  
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JMenu;  
import javax.swing.JMenuBar;  
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;  
import javax.swing.UIManager;  
import javax.swing.UnsupportedLookAndFeelException;  
import javax.swing.filechooser.FileFilter;  
import javax.swing.filechooser.FileNameExtensionFilter;  
import javax.swing.filechooser.FileView;  
  
public class FileChooserTest {  
  
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                ImageViewerFrame frame = new ImageViewerFrame();  
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                frame.setVisible(true);  
            }  
        });  
    }  
}  
  
class ImageViewerFrame extends JFrame implements ActionListener{  
	
	//窗口大小
    public static final int WIDTH = 800;  
    public static final int HEIGHT = 800;  
    //窗口大小
      
    private JLabel label_1_image1;
    private JLabel label_2_image2;
    private JButton button_1;
    private JFileChooser chooser; 
    private JTextField text_title;
    private JTextField text_value;
    BufferedImage image1=null;
    ImageIcon image2=null;
    BufferedImage image3=null;
    int newWidth;
    int newHeight;
    String tn_text;
	
      
    public ImageViewerFrame() {  
        super("ImageViewer");  
        setSize(WIDTH, HEIGHT);  //设置窗口大小
        try {  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
            //  
        }   
  
        JMenuBar menuBar = new JMenuBar();  
        setJMenuBar(menuBar);  
        JMenu menu = new JMenu("File");  
        JMenuItem openItem = new JMenuItem("open");  
        menu.add(openItem);  
        openItem.addActionListener(new FileOpenListener());  
        JMenuItem exitItem = new JMenuItem("exit");  
        menu.add(exitItem);  
        menuBar.add(menu);  
        exitItem.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                // TODO Auto-generated method stub  
                System.exit(0);  
            }  
        });  

        //use a label to display a image  
        label_1_image1 =new JLabel();  
        label_1_image1.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/3));
        label_1_image1.setOpaque(true);
        label_1_image1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label_1_image1.setBackground(new Color(249,249,249));
        label_1_image1.setBounds(0, 175, 350, 350);
        label_2_image2=new JLabel();
        label_2_image2.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/3));
        label_2_image2.setOpaque(true);
        label_2_image2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label_2_image2.setBackground(new Color(254, 254, 240));
        label_2_image2.setBounds(450, 175, 350, 350);
        
        //text
        text_title=new JTextField("孔隙度为:");
        text_title.setEnabled(false);
        text_title.setFont(new Font("黑体", Font.ITALIC, 15));
        text_title.setDisabledTextColor(Color.BLACK);
        text_title.setBounds(530, 530, 76, 20);
        //value
        text_value=new JTextField(" ");
        text_value.setEnabled(false);
        text_value.setFont(new Font("黑体", Font.ITALIC, 15));
        text_value.setDisabledTextColor(Color.BLACK);
        text_value.setBounds(606, 530, 85, 20);
        
        
        button_1=new JButton("计算");
        button_1.setBounds(350, 325, 100, 50);
        button_1.addActionListener(this);
        button_1.setActionCommand("calculate_Image");
      
        add(label_1_image1);
        add(label_2_image2);
        add(button_1);
        add(text_title);
        add(text_value);
        
          
        chooser=new JFileChooser();  
        FileNameExtensionFilter filter =new FileNameExtensionFilter("Image Files", "jpg","jpeg","gif");  
        chooser.setFileFilter(filter);  
        //预览  
        chooser.setAccessory(new ImagePreviewer(chooser));  
        //accessory 通常用于显示已选中文件的预览图像  
          
//      chooser.setFileView(new FileIconView(filter,new ImageIcon("palette.gif")));  
        chooser.setFileView(new FileIconView(filter, new ImageIcon()));  
        //设置用于检索 UI 信息的文件视图，如表示文件的图标或文件的类型描述。   
  
        this.setLayout(null);//设置Frame布局为null
        this.setResizable(false);
        
    }
      
    private class FileOpenListener implements ActionListener{  
        @Override  
        public void actionPerformed(ActionEvent e) {  
            // TODO Auto-generated method stub  
            chooser.setCurrentDirectory(new File("."));  
            int result=chooser.showOpenDialog(ImageViewerFrame.this);  
            if(result==JFileChooser.APPROVE_OPTION){  
                String name=chooser.getSelectedFile().getPath();  
                ImageIcon icon=new ImageIcon(name);  
             // 等比缩放条件  
              int imgWidth=icon.getIconWidth();  
              int imgHeight=icon.getIconHeight();  
              int conWidth=getWidth();  //获取窗口初始宽
              int conHeight=getHeight();  //获取窗口初始高
              int reImgWidth;  //记录窗口初始宽
              int reImgHeight;  //记录窗口初始高
              if(imgWidth/imgHeight>=conWidth/conHeight){  
                  if(imgWidth>conWidth){  
                      reImgWidth=conWidth;  
                      reImgHeight=imgHeight*reImgWidth/imgWidth;  
                  }else{  
                      reImgWidth=imgWidth;  
                      reImgHeight=imgHeight;  
                  }  
              }else{  
                  if(imgWidth>conWidth){  
                      reImgHeight=conHeight;  
                      reImgWidth=imgWidth*reImgHeight/imgHeight;  
                  }else{  
                      reImgWidth=imgWidth;  
                      reImgHeight=imgHeight;  
                  }  
              }  
                //这个是强制缩放到与组件(Label)大小相同  
                //icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), getHeight()-25, Image.SCALE_DEFAULT));  
                //这个是按等比缩放  
                icon=new ImageIcon(icon.getImage().getScaledInstance((reImgWidth/2)-(reImgWidth/10), (reImgHeight/2)-(reImgWidth/10), Image.SCALE_DEFAULT));  
                label_1_image1.setIcon(icon);  
                label_1_image1.setHorizontalAlignment(SwingConstants.CENTER);  
                
                newWidth=(reImgWidth/2)-(reImgWidth/10);
                newHeight=(reImgHeight/2)-(reImgWidth/10);
                
                try {
					image1=ImageIO.read(new File(name));
				    //image1.getGraphics().drawImage(image1.getScaledInstance((reImgWidth/2)-(reImgWidth/10), (reImgHeight/2)-(reImgWidth/10), Image.SCALE_SMOOTH), 0, 0, null);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//                label_2_image2.setIcon(icon);
//                label_2_image2.setHorizontalAlignment(SwingConstants.CENTER);
            }  
        }  
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("calculate_Image")){
			image2=new ImageIcon(grayImage(image1));
			image2=new ImageIcon(image2.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
			
			label_2_image2.setIcon(image2);
            label_2_image2.setHorizontalAlignment(SwingConstants.CENTER);
            
            text_value.setText(tn_text);
		}
	}  
	
	public BufferedImage grayImage(BufferedImage image){
		
		//
		float tn;
		float i=1;
		float j=1;
		//
		
		//获取图像宽高
		int width=image.getWidth();
		int height=image.getHeight();
		//灰度化
		float[] rgb = new float[3];  
        double[][] zuobiao = new double[width][height];  
        int R = 0;  
        float red = 0;  
        float green = 0;  
        float blue = 0;  
        BufferedImage image_bi= new BufferedImage(width, height,  
                BufferedImage.TYPE_BYTE_BINARY);;  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                int pixel = image.getRGB(x, y);   
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                red += rgb[0];  
                green += rgb[1];  
                blue += rgb[2];  
                R = (x+1) *(y+1);  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;  
                zuobiao[x][y] = avg;          
            }  
        }  
		//二值化
        double SW = 95;  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                if (zuobiao[x][y] <= SW) {  
                    int max = new Color(0, 0, 0).getRGB();  
                    image_bi.setRGB(x, y, max); 
                    
                    i=i+1;
                    
                }else{  
                    int min = new Color(255, 255, 255).getRGB();  
                    image_bi.setRGB(x, y, min);  
                    
                    j=j+1;
                    
                }  
            }             
        }
        //计算
        tn=(j/(i+j));
        tn_text=String.valueOf(tn);//将float类型的转换为String类型并赋给tn_text
        //
		return image_bi;
	}
  
	
}  
    
class FileIconView extends FileView {  
    public FileIconView(FileFilter aFilter,Icon anIcon){  
        filter=aFilter;  
        icon=anIcon;  
    }  
    public Icon getIcon(File f){  
        if(!f.isDirectory()&&filter.accept(f)){  
            return icon;  
        }else return null;  
    }  
    private FileFilter filter;  
    private Icon icon;  
}  
  
class ImagePreviewer extends JLabel{  
    public ImagePreviewer(JFileChooser chooser){  
        setPreferredSize(new Dimension(100,100));  //OpenFile窗口预览视图大小
        setBorder(BorderFactory.createEtchedBorder());  
        chooser.addPropertyChangeListener(new PropertyChangeListener() {  
            public void propertyChange(PropertyChangeEvent event) {  
                if(event.getPropertyName()==JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){  
                    File f=(File) event.getNewValue();  
                    if(f==null){  
                        setIcon(null);  
                        return;  
                    }  
                    ImageIcon icon=new ImageIcon(f.getPath()); //f记录选择图片的路径 
//                  if(icon.getIconWidth()>getWidth()){  
                        icon=new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));  
//                  }  
                    setIcon(icon);  
                }  
            }  
        });  
    }  
}  