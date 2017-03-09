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
            writer.write(drawLegend());
            writer.write(drawLines());

            int gap = 30;

            String line = bufferedReader.readLine();
            String[] data = line.split(",");

            for(int i = 1; i < data.length; i++) {

                Integer tmpY1 = 400 - 10 * Integer.parseInt(data[i-1]);
                Integer tmpY2 = 400 - 10 * Integer.parseInt(data[i]);

                writer.write(drawLine(gap - 20, tmpY1.toString(), gap, tmpY2.toString()));

                gap += 20;
            }

            writer.write("</svg>");
            writer.close();

        } catch (IOException ex) {}
    }

    public static String drawCanvas() {

        String header = "<?xml version=\"1.0\" standalone=\"yes\"?>";
        String data = "<svg width=\"810\" height=\"510\" version=\"1.1\" " +
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

    public static String drawLegend() {

        String legend =
                "<text x=\"10\" y=\"450\" fill=\"black\">LEGENDA: linia zielona 15+ |" +
                        " linia szara 10+ | linia czerwona 5+</text>\n";

        return legend;
    }

    public static String drawLines() {

        String greatLine =
                "<line x1=\"10\" y1=\"250\" x2=\"800\" y2=\"250\" style=\"stroke:rgb(51,204,51);stroke-width:0,5\" />\n";

        String mediumLine =
                "<line x1=\"10\" y1=\"300\" x2=\"800\" y2=\"300\" style=\"stroke:rgb(128,128,128);stroke-width:0,5\" />\n";

        String badLine =
                "<line x1=\"10\" y1=\"350\" x2=\"800\" y2=\"350\" style=\"stroke:rgb(255,51,51);stroke-width:0,5\" />\n";

        return greatLine + mediumLine + badLine;
    }
}
