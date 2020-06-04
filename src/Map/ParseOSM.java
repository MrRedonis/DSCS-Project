package Map;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ParseOSM {
    private KnotList kn_lst;
    private WayList w_lst;
    private LaneList l_lst;

    ParseOSM() {
        try {
            File inputFile = new File("map.osm");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setCoalescing(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("node");

            KnotList knot_list = new KnotList();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                HashMap<String,String> tags = new HashMap<String, String>();
                Knot knot = new Knot();
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    if(element.getAttribute("action").equals("delete")) continue;
                    long id = Long.parseLong(element.getAttribute("id"));
                    double lat = Double.parseDouble(element.getAttribute("lat"));
                    double lon = Double.parseDouble(element.getAttribute("lon"));
                    knot = new Knot(id,lat,lon);
                }

                NodeList in_nodes = node.getChildNodes();
                for (int j = 0; j < in_nodes.getLength(); j++) {
                    Node in_node = in_nodes.item(j);

                    if (in_node.getNodeType() == Node.ELEMENT_NODE) {
                        Element in_element = (Element) in_node;
                        tags.put(in_element.getAttribute("k"),in_element.getAttribute("v"));
                    }
                }
                knot.setTags(tags);
                knot_list.addKnot(knot);

                this.kn_lst = knot_list;
            }

            //Wczytywanie dróg
            NodeList ways = doc.getElementsByTagName("way");
            WayList way_list = new WayList();

            for (int i = 0; i < ways.getLength(); i++) {
                Node node = ways.item(i);
                Way way = new Way();
                HashMap<String, String> tags = new HashMap<String, String>();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if(element.getAttribute("action").equals("delete")) continue;
                    way.setId(Long.parseLong(element.getAttribute("id")));
                }

                NodeList in_nodes = node.getChildNodes();

                for (int j = 0; j < in_nodes.getLength(); j++) {
                    Node in_node = in_nodes.item(j);

                    if (in_node.getNodeType() == Node.ELEMENT_NODE) {
                        Element in_element = (Element) in_node;
                        if(in_element.getTagName().equals("nd")){
                            way.addKnot(Long.parseLong(in_element.getAttribute("ref")));
                        }
                        else{
                            tags.put(in_element.getAttribute("k"),in_element.getAttribute("v"));
                        }
                    }
                }
                way.setTags(tags);
                way_list.addWay(way);

                this.w_lst = way_list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        build();
    }

    private void build(){
        this.kn_lst = w_lst.buildKnotsRelations(kn_lst);
        this.l_lst = w_lst.buildLanes(kn_lst);
        this.l_lst.buildMaxSpeed();
    }

    public KnotList getKnotList() {
        return kn_lst;
    }

    public LaneList getLaneList() {
        return l_lst;
    }

    public void simulate() throws Exception {
        for (Map.Entry<Pair<Long, Long>, Lane> entry : l_lst.getLanes().entrySet()) {
            entry.getValue().simulate();
            while (!entry.getValue().outOfSection.isEmpty()){
                Car car = entry.getValue().outOfSection.poll();
                if(kn_lst.getRandomNextKnotId(entry.getKey().getValue()) != entry.getValue().getEnd().getId()){//Jeśli istnieje sąsiedni węzeł do ostatniego węzłą lane'a
                    long knot_id = kn_lst.getRandomNextKnotId(entry.getKey().getValue());//Pobierz id sąsiedniego węzła
                    l_lst.getLane(entry.getKey().getValue(),knot_id).keepDriving(car);//Dodaj pojazd do kolejnego lane'a
                }
            }
        }
    }

    public void addCars(int numCars) {
        ArrayList<ArrayList<Knot>> knot_array = kn_lst.getKnotsAsArray();
        Random random = new Random();
        for(int i=0;i<numCars;i++){
            long randomNode;
            long randomNodeNeighbour;
            do {
                randomNode = ThreadLocalRandom.current().nextInt(0,knot_array.size());
            }while (knot_array.get((int) randomNode).size()<2);
            randomNodeNeighbour =  ThreadLocalRandom.current().nextInt(1,knot_array.get((int) randomNode).size());
            l_lst.getLane(knot_array.get((int) randomNode).get(0).getId(),knot_array.get((int) randomNode).get((int) randomNodeNeighbour).getId()).addCar();
        }
    }
}

//Komendy Osmosis
//osmosis --read-xml map_z_busami.osm --tf accept-ways highway=* --used-node --write-xml map_r.osm  ## Wyciąganie wszystkich dróg i powiązanych z nimi węzłów