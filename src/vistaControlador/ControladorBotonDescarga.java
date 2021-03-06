package vistaControlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utilities.UtilidadesArchivos;

public class ControladorBotonDescarga implements Initializable
{
	@FXML private HBox hbox;
	@FXML private Text text;
	@FXML private Button boton;
	
	private ProgressIndicator progressIndicator;
	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private String key;
	private String name;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1);
		progressIndicator.setVisible(true);
		
		boton.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event) 
			{
				UtilidadesArchivos.receiveFile(key,"downloads/"+name);
				hbox.getChildren().remove(progressIndicator);
			}

		});
		
		boton.setOnMouseReleased(new EventHandler<MouseEvent>() 
		{
			@Override
			public void handle(MouseEvent event) 
			{
				hbox.getChildren().add(progressIndicator);
			}

		});
	}

}
