package edu.illinois.cs.cogcomp.temporal.utils.GraphVisualizer;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GraphJavaScript {
    private String fname;
    private final int width = 2000;
    private final int height = 1000;
    private final int linkDistance = 30;
    private final int circle_size = 10;
    private List<vertex> V;
    private HashMap<String,vertex> V_map;
    private List<edge> E;

    public GraphJavaScript(String fname){
        this.fname = fname;
        V = new ArrayList<>();
        V_map = new HashMap<>();
        E = new ArrayList<>();
    }

    public void addVertex(String uniqueid,String text, int colorId){
        vertex v = new vertex(uniqueid,text,colorId);
        V.add(v);
        V_map.put(uniqueid,v);
    }

    public void addEdge(String uniqueid1, String uniqueid2, String label, int length, int colorId, String markerEnd){
        if(!V_map.containsKey(uniqueid1)||!V_map.containsKey(uniqueid2))
            return;
        vertex v1 = V_map.get(uniqueid1);
        vertex v2 = V_map.get(uniqueid2);
        E.add(new edge(getVertexIdx(v1),getVertexIdx(v2),label,length*linkDistance, colorId, markerEnd));
    }

    public void sortVertexes(){
        V.sort(Comparator.comparing(vertex::getUniqueid));
    }
    public void createJS(){
        if(V.size()==0){
            System.out.println(fname+": Graph has not been initialized.");
        }
        if(E.size()==0)
            System.out.println(fname+": Graph has no edges.");
        String js_vertex = "";
        String js_edge = "";
        for(vertex v:V){
            String tmp = String.format("    {%s},\n",v.toString4d3());
            js_vertex += tmp;
        }
        for(edge e:E){
            String tmp = String.format("    {%s},\n",e.toString4d3());
            js_edge += tmp;
        }
        String jscript = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>Force Layout with labels on edges</title>\n" +
                "<script src=\"http://d3js.org/d3.v3.min.js\" charset=\"utf-8\"></script>\n" +
                "<style type=\"text/css\">\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "\n" +
                "    var w = "+width+"\n" +
                "    var h = "+height+"\n" +
                "    var linkDistance="+linkDistance+";\n" +
                "    var circle_size="+circle_size+";\n"+
                "\n" +
                "    var colors = d3.scale.category10();\n" +
                "\t\n" +
                "    var dataset = {\n" +
                "\n" +
                "    nodes: [\n" +
                js_vertex+
                "    ],\n" +
                "    edges: [\n" +
                js_edge+
                "    ]\n" +
                "    };\n" +
                "\n" +
                " \n" +
                "    var svg = d3.select(\"body\").append(\"svg\").attr({\"width\":w,\"height\":h});\n" +
                "\n" +
                "    var force = d3.layout.force()\n" +
                "        .nodes(dataset.nodes)\n" +
                "        .links(dataset.edges)\n" +
                "        .size([w,h])\n" +
                "        .linkDistance(function(d) {return d.len;})\n" +
                "        .charge([-500])\n" +
                "        .theta(0.1)\n" +
                "        .gravity(0.05)\n" +
                "        .start();\n" +
                "\n" +
                " \n" +
                "\n" +
                "    var edges = svg.selectAll(\"line\")\n" +
                "      .data(dataset.edges)\n" +
                "      .enter()\n" +
                "      .append(\"line\")\n" +
                "      .attr(\"id\",function(d,i) {return 'edge'+i})\n" +
                "      .attr('marker-end',function(d,i){return 'url(#' + d.markerend + ')';})\n" +
                "      .style(\"stroke\",function(d,i){return colors(d.color);})\n" +
                "      .style(\"pointer-events\", \"none\");\n" +
                "    \n" +
                "    var nodes = svg.selectAll(\"circle\")\n" +
                "      .data(dataset.nodes)\n" +
                "      .enter()\n" +
                "      .append(\"circle\")\n" +
                "      .attr({\"r\":circle_size})\n" +
                "      .style(\"fill\",function(d,i){return colors(d.color);})\n" +
                "      .call(force.drag)\n" +
                "\n" +
                "\n" +
                "    var nodelabels = svg.selectAll(\".nodelabel\") \n" +
                "       .data(dataset.nodes)\n" +
                "       .enter()\n" +
                "       .append(\"text\")\n" +
                "       .attr({\"x\":function(d){return d.x;},\n" +
                "              \"y\":function(d){return d.y;},\n" +
                "              \"class\":\"nodelabel\",\n" +
                "              \"stroke\":\"black\"})\n" +
                "       .text(function(d){return d.name;});\n" +
                "\n" +
                "    var edgepaths = svg.selectAll(\".edgepath\")\n" +
                "        .data(dataset.edges)\n" +
                "        .enter()\n" +
                "        .append('path')\n" +
                "        .attr({'d': function(d) {return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y},\n" +
                "               'class':'edgepath',\n" +
                "               'fill-opacity':0,\n" +
                "               'stroke-opacity':0,\n" +
                "               'fill':'blue',\n" +
                "               'stroke':'red',\n" +
                "               'id':function(d,i) {return 'edgepath'+i}})\n" +
                "        .style(\"pointer-events\", \"none\");\n" +
                "\n" +
                "    var edgelabels = svg.selectAll(\".edgelabel\")\n" +
                "        .data(dataset.edges)\n" +
                "        .enter()\n" +
                "        .append('text')\n" +
                "        .style(\"pointer-events\", \"none\")\n" +
                "        .attr({'class':'edgelabel',\n" +
                "               'id':function(d,i){return 'edgelabel'+i},\n" +
                "               'dx':linkDistance/2,\n" +
                "               'dy':0,\n" +
                "               'font-size':20,\n" +
                "               'fill':'#aaa'});\n" +
                "\n" +
                "    edgelabels.append('textPath')\n" +
                "        .attr('xlink:href',function(d,i) {return '#edgepath'+i})\n" +
                "        .style(\"pointer-events\", \"none\")\n" +
                "        .text(function(d){return d.label});\n" +
                "\n" +
                "\n" +
                "    svg.append('defs').append('marker')\n" +
                "        .attr({'id':'arrowhead',\n" +
                "               'viewBox':'-0 -5 10 10',\n" +
                "               'refX':25,\n" +
                "               'refY':0,\n" +
                "               //'markerUnits':'strokeWidth',\n" +
                "               'orient':'auto',\n" +
                "               'markerWidth':10,\n" +
                "               'markerHeight':10,\n" +
                "               'xoverflow':'visible'})\n" +
                "        .append('svg:path')\n" +
                "            .attr('d', 'M 0,-5 L 10 ,0 L 0,5')\n" +
                "            .attr('fill', '#000')\n" +
                "            .attr('stroke','#000');\n" +
                "     \n" +
                "\n" +
                "    force.on(\"tick\", function(){\n" +
                "\n" +
                "        edges.attr({\"x1\": function(d){return d.source.x;},\n" +
                "                    \"y1\": function(d){return d.source.y;},\n" +
                "                    \"x2\": function(d){return d.target.x;},\n" +
                "                    \"y2\": function(d){return d.target.y;}\n" +
                "        });\n" +
                "\n" +
                "        nodes.attr({\"cx\":function(d){return d.x;},\n" +
                "                    \"cy\":function(d){return d.y;}\n" +
                "        });\n" +
                "\n" +
                "        nodelabels.attr(\"x\", function(d) { return d.x; }) \n" +
                "                  .attr(\"y\", function(d) { return d.y; });\n" +
                "\n" +
                "        edgepaths.attr('d', function(d) { var path='M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y;\n" +
                "                                           //console.log(d)\n" +
                "                                           return path});       \n" +
                "\n" +
                "        edgelabels.attr('transform',function(d,i){\n" +
                "            if (d.target.x<d.source.x){\n" +
                "                bbox = this.getBBox();\n" +
                "                rx = bbox.x+bbox.width/2;\n" +
                "                ry = bbox.y+bbox.height/2;\n" +
                "                return 'rotate(180 '+rx+' '+ry+')';\n" +
                "                }\n" +
                "            else {\n" +
                "                return 'rotate(0)';\n" +
                "                }\n" +
                "        });\n" +
                "    });\n" +
                "\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        try{
            File file = new File(fname);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jscript);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getVertexIdx(vertex v){
        for(int i=0;i<V.size();i++){
            if(v.equals(V.get(i)))
                return i;
        }
        return -1;
    }

    public static void main(String[] args) throws Exception{
        /*myDatasetLoader loader = new myDatasetLoader();
        String htmlDir = "data/html/TBDense_Test_autoCorrected_reduced";
        IOUtils.mkdir(htmlDir);
        List<myTemporalDocument> docs = loader.getTBDense_Test_autoCorrected();
        for(myTemporalDocument doc:docs){
            System.out.println(doc.getDocid());
            doc.getGraph().reduction();
            doc.getGraph().graphVisualization(htmlDir);
            doc.getGraph().chainVisualization(htmlDir);
        }*/
    }
}
