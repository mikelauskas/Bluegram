package vistaControlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;

public class ControladorPrincipal implements Initializable
{
	@FXML
	private Pane panel_comienzo;
	@FXML
	private Pane panel_settings;
	@FXML
	private Pane panel_about;
	@FXML
	private Pane panel_salir;

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		panel_comienzo.setStyle("-fx-background-color: red;");
		panel_settings.setStyle("-fx-background-color: yellow;");
		panel_about.setStyle("-fx-background-color: deepskyblue;");
		panel_salir.setStyle("-fx-background-color: lime;");

		panel_comienzo.setOnMouseClicked(new EventHandler<MouseEvent>() {//evento ventana login

			@Override
			public void handle(MouseEvent event) 
			{
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(Main.class.getResource("/vistaControlador/.fxml"));
				AnchorPane conectionLayout=null;
				try 
				{
					conectionLayout=loader.load();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				Scene escena=new Scene(conectionLayout);
				Stage stg=(Stage)panel_comienzo.getScene().getWindow();
				stg.setScene(escena);
				stg.show();
			}
				
		});
		
		panel_comienzo.setOnMousePressed(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_comienzo.setStyle("-fx-background-color: firebrick;");	
			}
				
		});
		
		panel_comienzo.setOnMouseReleased(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_comienzo.setStyle("-fx-background-color: red;");	
			}
				
		});
		
		panel_settings.setOnMouseClicked(new EventHandler<MouseEvent>() {//evento ventana registro

			@Override
			public void handle(MouseEvent event) 
			{
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(ControladorPrincipal.class.getResource("/vistaControlador/Registro.fxml"));
				AnchorPane conectionLayout=null;
				try 
				{
					conectionLayout=loader.load();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				Scene escena=new Scene(conectionLayout);
				Stage stg=(Stage)panel_comienzo.getScene().getWindow();
				stg.setScene(escena);
				stg.show();
			}
				
		});
		
		panel_settings.setOnMousePressed(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_settings.setStyle("-fx-background-color: gold;");	
			}
				
		});
		
		panel_settings.setOnMouseReleased(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_settings.setStyle("-fx-background-color: yellow;");	
			}
				
		});
		
		panel_about.setOnMouseClicked(new EventHandler<MouseEvent>() {//evento ventana sobre nosotros

			@Override
			public void handle(MouseEvent event) 
			{

			}
				
		});
		
		panel_about.setOnMousePressed(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_about.setStyle("-fx-background-color: dodgerblue;");	
			}
				
		});
		
		panel_about.setOnMouseReleased(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_about.setStyle("-fx-background-color: deepskyblue;");	
			}
				
		});
		
		panel_salir.setOnMouseClicked(new EventHandler<MouseEvent>() {//evento salir

			@Override
			public void handle(MouseEvent event) 
			{
				Stage stage;
				stage=(Stage) panel_salir.getScene().getWindow();
				stage.close();	
			}
				
		});
		
		panel_salir.setOnMousePressed(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_salir.setStyle("-fx-background-color: limegreen;");	
			}
				
		});
		
		panel_salir.setOnMouseReleased(new EventHandler<MouseEvent>() {

		    @Override
			public void handle(MouseEvent event) 
			{
		    	panel_salir.setStyle("-fx-background-color: lime;");	
			}
				
		});
	}

}