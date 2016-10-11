package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;


public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

//	private JTextField coordinates;
	private JMapViewer map;
	
	private JTextField day;
	private JTextField month;
	private JTextField year;
	private JTextField hour;
	private JTextField min;

	private JTextArea info;
	private JTextArea suggestions;

	// <amenity>
	private JCheckBox bar, bbq, biergarten, cafe, fast_food, ice_cream, pub, restaurant, library, public_bookcase,
			casino, cinema, nightclub, theatre, marketplace, place_of_worship;

	// <leisure>
	private JCheckBox amusement_arcade, adult_gaming_centre, dance, dog_park, fishing, nature_reserve, swimming_pool,
			swimming_area, sport_centre, stadium, track, water_park;

	// <shop>
	private JCheckBox alcohol, bakery, seafood, wine, supermarket, mall, bag, clothes, fashion, shoes, watches, beauty,
			tattoo, hardware, doityourself, antiques, carpet, computer, eletronics, hifi, mobile_phone, bicycle,
			motorcycle, scuba_diving, art, collector, games, music, musical_instrument, photo, video, video_games,
			books, lottery, tobacco, toys;

	// <tourism>
	private JCheckBox alpine_hut, aquarium, artwork, attraction, gallery, zoo, theme_park, picnic_site, museum,
			camp_site;

	// <generals>
	private JCheckBox historic, sport;
	
	private JCheckBox selectAll;

	private JButton go;

	private Controller controller;
	
	private List<JCheckBox> checksGeneral = new ArrayList<>();
	private List<JCheckBox> checksAmenity = new ArrayList<>();
	private List<JCheckBox> checksLeisure = new ArrayList<>();
	private List<JCheckBox> checksShop = new ArrayList<>();
	private List<JCheckBox> checksTourism = new ArrayList<>();
	
	
	private class InteractiveMapController extends DefaultMapController {

		public InteractiveMapController(JMapViewer map) {
			super(map);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
				if (map.getMapMarkerList().size() > 0)
					map.getMapMarkerList().remove(map.getMapMarkerList().size() - 1);

				ICoordinate coordinate = map.getPosition(e.getPoint());
				controller.setActualPosition(coordinate.getLat(), coordinate.getLon());
				System.out.println(coordinate);
				
				MapMarkerDot mmd = new MapMarkerDot(new Coordinate(coordinate.getLat(), coordinate.getLon()));
				map.addMapMarker(mmd);
			} else if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				map.zoomIn(e.getPoint());
			}
		}

	}
	

	public MainFrame(Controller controller) {
		checksAmenity = new ArrayList<JCheckBox>();
		checksLeisure = new ArrayList<JCheckBox>();
		checksShop = new ArrayList<JCheckBox>();
		checksTourism = new ArrayList<JCheckBox>();
		this.controller = controller;
		initGui();
		pack();
	}
	

	public JTextArea getProbability() {
		return this.info;
	}

	public void setProbability(JTextArea probability) {
		this.info = probability;
	}

	public JTextArea getSuggestions() {
		return this.suggestions;
	}

	public void setSuggestions(JTextArea suggestions) {
		this.suggestions = suggestions;
	}

	public JMapViewer getMap() {
		return this.map;
	}

	public JTextField getDay() {
		return this.day;
	}

	public void setDay(JTextField day) {
		this.day = day;
	}

	public JTextField getMonth() {
		return this.month;
	}

	public void setMonth(JTextField month) {
		this.month = month;
	}

	public JTextField getYear() {
		return this.year;
	}

	public void setYear(JTextField year) {
		this.year = year;
	}

	public JTextField getHour() {
		return this.hour;
	}

	public void setHour(JTextField hour) {
		this.hour = hour;
	}

	public JTextField getMin() {
		return this.min;
	}

	public void setMin(JTextField min) {
		this.min = min;
	}

	private void initGui() { // inizializzazione di tutti i componenti e setting
								// dei vari layout

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel mapPanel = new JPanel();
		mapPanel.setBorder(new TitledBorder(new EtchedBorder(), "Click to set a position"));
		map = new JMapViewer();
		mapPanel.add(map);
		@SuppressWarnings("unused")
		InteractiveMapController imc = new InteractiveMapController(map);
		mainPanel.add(mapPanel, BorderLayout.LINE_END);
		
		JPanel tbPanel = new JPanel();
		tbPanel.setLayout(new GridLayout());
		mainPanel.add(tbPanel, BorderLayout.PAGE_START);

//		coordinates = new JTextField();
		day = new JTextField();
		month = new JTextField();
		year = new JTextField();
		hour = new JTextField();
		min = new JTextField();
		
		controller.setPanelDate(day, month, year, hour, min);
		
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Destinations' info"));
		info = new JTextArea(10,10);
		info.setEditable(false);
		JScrollPane pScroll = new JScrollPane(info);
		pScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		infoPanel.add(pScroll, BorderLayout.CENTER);
		

		JPanel destinationPanel = new JPanel(new BorderLayout());
		destinationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Destinations"));
		suggestions = new JTextArea(10,10);
		suggestions.setEditable(false); // set textArea non-editable
		JScrollPane dScroll = new JScrollPane(suggestions);
		dScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		destinationPanel.add(dScroll, BorderLayout.CENTER);
		
		selectAll = new JCheckBox("select all", false);
		selectAll.addActionListener(this);

		sport = new JCheckBox("sport", false);
		historic = new JCheckBox("historic", false);

		bar = new JCheckBox("bar", false);
		bbq = new JCheckBox("bbq", false);
		biergarten = new JCheckBox("biergarten", false);
		cafe = new JCheckBox("cafe", false);
		fast_food = new JCheckBox("fast_food", false);
		ice_cream = new JCheckBox("ice_cream", false);
		pub = new JCheckBox("pub", false);
		restaurant = new JCheckBox("restaurant", false);
		library = new JCheckBox("library", false);
		public_bookcase = new JCheckBox("public_bookcase", false);
		casino = new JCheckBox("casino", false);
		cinema = new JCheckBox("cinema", false);
		nightclub = new JCheckBox("nightclub", false);
		theatre = new JCheckBox("theatre", false);
		marketplace = new JCheckBox("marketplace", false);
		place_of_worship = new JCheckBox("place_of_worship", false);
		amusement_arcade = new JCheckBox("amusement_arcade", false);
		adult_gaming_centre = new JCheckBox("adult_gaming_centre", false);
		dance = new JCheckBox("dance", false);
		dog_park = new JCheckBox("dog_park", false);
		fishing = new JCheckBox("fishing", false);
		nature_reserve = new JCheckBox("nature_reserve", false);
		swimming_pool = new JCheckBox("swimming_pool", false);
		swimming_area = new JCheckBox("swimming_area", false);
		sport_centre = new JCheckBox("sport_centre", false);
		stadium = new JCheckBox("stadium", false);
		track = new JCheckBox("track", false);
		water_park = new JCheckBox("water_park", false);
		alcohol = new JCheckBox("alcohol", false);
		bakery = new JCheckBox("bakery", false);
		seafood = new JCheckBox("seafood", false);
		wine = new JCheckBox("wine", false);
		supermarket = new JCheckBox("supermarket", false);
		mall = new JCheckBox("mall", false);
		bag = new JCheckBox("bag", false);
		clothes = new JCheckBox("clothes", false);
		fashion = new JCheckBox("fashion", false);
		shoes = new JCheckBox("shoes", false);
		watches = new JCheckBox("watches", false);
		beauty = new JCheckBox("beauty", false);
		tattoo = new JCheckBox("tattoo", false);
		hardware = new JCheckBox("hardware", false);
		doityourself = new JCheckBox("doityourself", false);
		antiques = new JCheckBox("antiques", false);
		carpet = new JCheckBox("carpet", false);
		computer = new JCheckBox("computer", false);
		eletronics = new JCheckBox("eletronics", false);
		hifi = new JCheckBox("hifi", false);
		mobile_phone = new JCheckBox("mobile_phone", false);
		bicycle = new JCheckBox("bicycle", false);
		motorcycle = new JCheckBox("motorcycle", false);
		scuba_diving = new JCheckBox("scuba_diving", false);
		swimming_pool = new JCheckBox("swimming_pool", false);
		art = new JCheckBox("art", false);
		collector = new JCheckBox("collector", false);
		games = new JCheckBox("games", false);
		music = new JCheckBox("music", false);
		musical_instrument = new JCheckBox("musical_instrument", false);
		photo = new JCheckBox("photo", false);
		video = new JCheckBox("video", false);
		video_games = new JCheckBox("video_games", false);
		books = new JCheckBox("books", false);
		lottery = new JCheckBox("lottery", false);
		tobacco = new JCheckBox("tobacco", false);
		toys = new JCheckBox("toys", false);
		alpine_hut = new JCheckBox("alpine_hut", false);
		aquarium = new JCheckBox("aquarium", false);
		artwork = new JCheckBox("artwork", false);
		attraction = new JCheckBox("attraction", false);
		gallery = new JCheckBox("gallery", false);
		zoo = new JCheckBox("zoo", false);
		theme_park = new JCheckBox("theme_park", false);
		picnic_site = new JCheckBox("picnic_site", false);
		museum = new JCheckBox("museum", false);
		camp_site = new JCheckBox("camp_site", false);

		checksGeneral.add(sport);
		checksGeneral.add(historic);

		checksAmenity.add(bar);
		checksAmenity.add(bbq);
		checksAmenity.add(biergarten);
		checksAmenity.add(cafe);
		checksAmenity.add(fast_food);
		checksAmenity.add(ice_cream);
		checksAmenity.add(pub);
		checksAmenity.add(restaurant);
		checksAmenity.add(library);
		checksAmenity.add(public_bookcase);
		checksAmenity.add(casino);
		checksAmenity.add(cinema);
		checksAmenity.add(nightclub);
		checksAmenity.add(theatre);
		checksAmenity.add(marketplace);
		checksAmenity.add(place_of_worship);

		checksLeisure.add(amusement_arcade);
		checksLeisure.add(adult_gaming_centre);
		checksLeisure.add(dance);
		checksLeisure.add(dog_park);
		checksLeisure.add(fishing);
		checksLeisure.add(nature_reserve);
		checksLeisure.add(swimming_pool);
		checksLeisure.add(swimming_area);
		checksLeisure.add(sport_centre);
		checksLeisure.add(stadium);
		checksLeisure.add(track);
		checksLeisure.add(water_park);

		checksShop.add(alcohol);
		checksShop.add(bakery);
		checksShop.add(seafood);
		checksShop.add(wine);
		checksShop.add(supermarket);
		checksShop.add(mall);
		checksShop.add(bag);
		checksShop.add(clothes);
		checksShop.add(fashion);
		checksShop.add(shoes);
		checksShop.add(watches);
		checksShop.add(beauty);
		checksShop.add(tattoo);
		checksShop.add(hardware);
		checksShop.add(doityourself);
		checksShop.add(antiques);
		checksShop.add(carpet);
		checksShop.add(computer);
		checksShop.add(eletronics);
		checksShop.add(hifi);
		checksShop.add(mobile_phone);
		checksShop.add(bicycle);
		checksShop.add(motorcycle);
		checksShop.add(scuba_diving);
		checksShop.add(swimming_pool);
		checksShop.add(art);
		checksShop.add(collector);
		checksShop.add(games);
		checksShop.add(music);
		checksShop.add(musical_instrument);
		checksShop.add(photo);
		checksShop.add(video);
		checksShop.add(video_games);
		checksShop.add(books);
		checksShop.add(lottery);
		checksShop.add(tobacco);
		checksShop.add(toys);

		checksTourism.add(alpine_hut);
		checksTourism.add(aquarium);
		checksTourism.add(artwork);
		checksTourism.add(attraction);
		checksTourism.add(gallery);
		checksTourism.add(zoo);
		checksTourism.add(theme_park);
		checksTourism.add(picnic_site);
		checksTourism.add(museum);
		checksTourism.add(camp_site);

		go = new JButton("go");
		go.addActionListener(this);

		tbPanel.add(selectAll);
		tbPanel.add(new JLabel("                 day:"));
		tbPanel.add(day);
		tbPanel.add(new JLabel("               month:"));
		tbPanel.add(month);
		tbPanel.add(new JLabel("                year:"));
		tbPanel.add(year);
		tbPanel.add(new JLabel("                hour:"));
		tbPanel.add(hour);
		tbPanel.add(new JLabel("                 min:"));
		tbPanel.add(min);
		tbPanel.add(go);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		
		// shop
		JPanel shopPanel = new JPanel();
		shopPanel.setLayout(new GridLayout(20, 3));
		shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.add(shopPanel);

		JLabel shopLabel = new JLabel("   SHOP");
		shopLabel.setForeground(Color.RED);
		shopPanel.add(shopLabel);

		for (JCheckBox cb : checksShop) {
			shopPanel.add(cb);
		}


		// amenity
		JPanel amenityPanel = new JPanel();
		amenityPanel.setLayout(new BoxLayout(amenityPanel, BoxLayout.Y_AXIS));
		amenityPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.add(amenityPanel);

		JLabel amenityLabel = new JLabel("   AMENITY");
		amenityLabel.setForeground(Color.RED);
		amenityPanel.add(amenityLabel);

		for (JCheckBox cb : checksAmenity) {
			amenityPanel.add(cb);
		}

		// leisure
		JPanel leisurePanel = new JPanel();
		leisurePanel.setLayout(new BoxLayout(leisurePanel, BoxLayout.Y_AXIS));
		leisurePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.add(leisurePanel);

		JLabel leisureLabel = new JLabel("   LEISURE");
		leisureLabel.setForeground(Color.RED);
		leisurePanel.add(leisureLabel);

		for (JCheckBox cb : checksLeisure) {
			leisurePanel.add(cb);
		}

		// tourism
		JPanel tourismPanel = new JPanel();
		tourismPanel.setLayout(new BoxLayout(tourismPanel, BoxLayout.Y_AXIS));
		tourismPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.add(tourismPanel);

		JLabel tourismLabel = new JLabel("   TOURISM");
		tourismLabel.setForeground(Color.RED);
		tourismPanel.add(tourismLabel);

		for (JCheckBox cb : checksTourism) {
			tourismPanel.add(cb);
		}

		// generals
		JPanel generalPanel = new JPanel();
		generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
		generalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.add(generalPanel);

		JLabel generalLabel = new JLabel("   GENERALS");
		generalLabel.setForeground(Color.RED);
		generalPanel.add(generalLabel);

		for (JCheckBox cb : checksGeneral) {
			generalPanel.add(cb);
		}

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

		bottomPanel.add(destinationPanel);
		bottomPanel.add(infoPanel);

		getContentPane().add(mainPanel, BorderLayout.PAGE_START);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void selectByCategory(List<JCheckBox> checks, boolean value) {
		for (JCheckBox cb : checks)
			cb.setSelected(value);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == go) {
			try {
				info.setText("");
				
				controller.resetPreferences();

				controller.fillPreferencesByCategory("amenity", checksAmenity);
				controller.fillPreferencesByCategory("leisure", checksLeisure);
				controller.fillPreferencesByCategory("shop", checksShop);
				controller.fillPreferencesByCategory("tourism", checksTourism);
		
				if (sport.isSelected())
					controller.addPreference("sport", null);
		
				if (historic.isSelected())
					controller.addPreference("historic", null);

				// parsing dei dati di input
				controller.setDate(day, month, year, hour, min);				
				controller.getSuggest(this);

			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
				exc.printStackTrace();
			}
		} else if (e.getSource() == selectAll) {
			selectByCategory(checksAmenity, selectAll.isSelected());
			selectByCategory(checksGeneral, selectAll.isSelected());
			selectByCategory(checksLeisure, selectAll.isSelected());
			selectByCategory(checksShop, 	selectAll.isSelected());
			selectByCategory(checksTourism, selectAll.isSelected());
		}
	}
}
