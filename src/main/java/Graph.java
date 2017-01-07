import java.io.*;

/**
 * Created by Michał on 2017-01-07.
 */
public class Graph {

    public static void drawGraph(File target, File source) {

        try {

            FileWriter writer = new FileWriter(target);
            FileReader reader = new FileReader(source);
            BufferedReader bufferedReader = new BufferedReader(reader);

            writer.write(drawCanvas());
            writer.write(drawAxis());

            int gap = 30;

            String line = bufferedReader.readLine();
            String[] data = line.split(",");

            for(int i = 1; i < data.length; i++) {

                Integer tmpY1 = 400 - Integer.parseInt(data[i-1]);
                Integer tmpY2 = 400 - Integer.parseInt(data[i]);

                writer.write(drawLine(gap - 20, tmpY1.toString(), gap, tmpY2.toString()));

                gap += 20;
            }

            writer.write("</svg>");
            writer.close();

        } catch (IOException ex) {}
    }

    public static String drawCanvas() {

        String header = "<?xml version=\"1.0\" standalone=\"yes\"?>";
        String data = "<svg width=\"810\" height=\"410\" version=\"1.1\" " +
                "xmlns=\"http://www.w3.org/2000/svg\">\n";

        return header + data;
    }

    public static String drawAxis() {

        String verticalAxis =
                "<line x1=\"10\" y1=\"10\" x2=\"10\" y2=\"400\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />\n";

        String horizontalAxis =
                "<line x1=\"10\" y1=\"400\" x2=\"800\" y2=\"400\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />\n";

        return verticalAxis + horizontalAxis;
    }

    public static String drawLine(int x1, String y1, int x2, String y2) {

        String line =
                "<line x1=\"" + x1 + "\" y1=\""+ y1 + "\" x2=\"" + x2 +
                        "\" y2=\"" + y2 + "\" style=\"stroke:rgb(77,148,255);stroke-width:2\" />\n";

        return line;
    }

}
