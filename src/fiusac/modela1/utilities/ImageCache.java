/**
 * 
 */
package fiusac.modela1.utilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
/**
 * @author david
 *
 */
public class ImageCache {
	/**guarda las imagenes**/
	private HashMap<String, BufferedImage> sprites;
	/**
	 * 
	 */
	public ImageCache() {
		sprites = new HashMap<>();
	}
	/**
     * Devuelve la imagen segun el directorio
     * Se supone que la cadena del directorio este rodeado de comillas.
     * @param directorio Proveer el directorio absoluto o relativo
     * @return La imagen en memoria
     **/
    private BufferedImage loadImage(String directorio){
        URL url = null;
        try {
            directorio = directorio.replaceAll("(^\\\"|\\\"$)","");
            url = getClass().getClassLoader().getResource(directorio);
            if (url == null){//es un posible path absoluto
                url = new URL("file:" + (directorio.startsWith(File.separator) ?"":File.separator) + directorio);
            }
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen " + directorio + " de " + url);
            System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());
            //System.exit(0);
            return null;
        }
    }
    /**
     * Devuelve el recurso de la imagen. Si no existe en el hashmap
     * busca cargarla desde el archivo
     * @param nombre Directorio absoluto o relativo de la imagen
     * @return Null si no se encuentra la imagen y no se guardara en el hashmap
     **/
    public BufferedImage getSprite (String nombre){
        BufferedImage img = sprites.get(nombre);
        if (img == null){
            img = loadImage(nombre);
            if (img == null) return null;//no agregar al map
            sprites.put(nombre,img);
        }
        return img;
    }
}
