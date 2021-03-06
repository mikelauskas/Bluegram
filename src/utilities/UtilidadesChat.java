package utilities;

import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import datos.FicheroXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import modelo.Contacto;
import vistaControlador.ControladorBotonDescarga;
import vistaControlador.ControladorChat;
import vistaControlador.ControladorConfiguracion;

public class UtilidadesChat {
	public Map<String, Contacto> getContacts() {
		Roster roster = Roster.getInstanceFor(UtilidadesServidor.scon);

		if (!roster.isLoaded()) {
			try {
				roster.reloadAndWait();
			} catch (NotLoggedInException e) {
				e.printStackTrace();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Map<String, Contacto> contactos = new HashMap<String, Contacto>();
		Contacto cc;
		ControladorConfiguracion config = new ControladorConfiguracion();
		String username = UtilidadesServidor.scon.getUser().split("/")[0];

		for (RosterEntry entry : roster.getEntries()) {
			cc = new Contacto();
			cc.setFriend(true);
			cc.setSelected(false);
			cc.setId(entry.getUser() + "@" + Constantes.serviceName);
			cc.setNombre(entry.getName());
			cc.setPresencia(roster.getPresence(entry.getUser()).toString());

			switch (config.getConfig().getAlmacenamiento()) {
			case ("Online"): {
				List<Message> lista = UtilidadesConversacion.getOnlineHistory(new BD(), cc.getId(), username);
				FicheroXML.escribeFichero(lista, cc.getId() + username.split("@")[0]);
				cc.setMensajes(lista);
				break;
			}
			case ("Local"): {
				cc.setMensajes(FicheroXML.leeFichero(username.split("@")[0] + entry.getUser()));
				break;
			}
			}
			contactos.put(cc.getId(), cc);
		}

		return contactos;
	}

	public void anadirContacto() {
		Roster roster = Roster.getInstanceFor(UtilidadesServidor.scon);
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Añadir contacto");
		dialog.setContentText("Inserte el ID del contacto:");

		Optional<String> result = dialog.showAndWait();

		if (new BD().buscaUsuario(result.get())) {
			try {
				roster.createEntry(result.get(), result.get(), null);
				roster.reload();
			} catch (NoResponseException e) {
				System.out.println("Error de no respuesta");
			} catch (XMPPErrorException e) {
				System.out.println("Error de XMPP");
			} catch (NotConnectedException e) {
				System.out.println("Error de no conexion");
			} catch (NotLoggedInException e) {
				System.out.println("Error de no logeado");
			}

		} else {
			UtilidadesOtros.alerta(AlertType.INFORMATION, "Aviso",
					"El usuario introducido no esta registrado en la aplicacion");
		}
	}

	public void eliminarContacto(String string) {
		Roster roster = Roster.getInstanceFor(UtilidadesServidor.scon);

		RosterEntry re = roster.getEntry(string);
		try {
			roster.removeEntry(re);
			roster.reload();
		} catch (NoResponseException e) {
			System.out.println("Error de no respuesta");
		} catch (XMPPErrorException e) {
			System.out.println("Error de XMPP");
		} catch (NotConnectedException e) {
			System.out.println("Error de no conexion");
		} catch (NotLoggedInException e) {
			System.out.println("Error de no logeado");
		}
	}

	public List<Message> getOfflineMessages() throws NoResponseException, XMPPErrorException, NotConnectedException {
		OfflineMessageManager omm = new OfflineMessageManager(UtilidadesServidor.scon);
		List<Message> mensajes = null;

		if (omm.getMessageCount() == 0) {
			mensajes = new ArrayList<Message>();
			System.out.println("0 mensajes offline");
		} else {
			mensajes = omm.getMessages();
			omm.deleteMessages();
			System.out.println("varios mensajes offline->" + mensajes.size());
		}
		return mensajes;
	}

	@SuppressWarnings("serial")
	public void asignaMensajes(List<Message> mensajesOff) {
		for (Message mensaje : mensajesOff) {
			Contacto cto = ControladorChat.contactos.get(mensaje.getFrom().split("/")[0]);

			if (cto != null) {
				cto.addMessage(mensaje);
			} else {
				cto = new Contacto();
				cto.setNombre(mensaje.getFrom().split("@")[0]);
				cto.setId(mensaje.getFrom().split("/")[0]);
				cto.setFriend(false);
				cto.setMensajes(new ArrayList<Message>() {
					{
						add(mensaje);
					}
				});
			}
			ControladorChat.contactos.put(cto.getId(), cto);
			FicheroXML.escribeFichero(cto.getMensajes(),
					UtilidadesServidor.scon.getUser().split("@")[0] + cto.getNombre());
		}
		mensajesOff.clear();
	}

	public static void labelGenerator(String texto, Pos pos, String color) {
		StackPane pane = new StackPane();
		Text txt = new Text();
		HBox hbox = new HBox();

		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics();
		double width = fm.stringWidth(texto);

		pane.getChildren().add(txt);
		txt.setText(texto);
		txt.setFont(Font.font("Verdana", 11));

		if (width > 150) {
			txt.setWrappingWidth(150);
			pane.setMaxWidth(150);
			pane.setPrefWidth(150);
		} else {
			pane.setMaxWidth(width);
			pane.setPrefWidth(width);
		}

		pane.setStyle(
				"-fx-background-color: " + color + "; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
		StackPane.setAlignment(txt, pos);
		pane.setPadding(new Insets(5, 5, 5, 5));

		hbox.getChildren().add(pane);
		hbox.setAlignment(pos);

		ControladorChat.conversacionActual.setMargin(hbox, new Insets(5, 5, 5, 5));
		ControladorChat.conversacionActual.addChildren(hbox);
	}

	public void downloadGenerator(String name, String key) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistaControlador/Descarga.fxml"));

		try {
			ControladorChat.conversacionActual.addChildren(loader.load());
			ControladorBotonDescarga cbd = loader.getController();
			cbd.setName(name);
			cbd.setKey(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
