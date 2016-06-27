package proj1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfoController {

    @RequestMapping(value="/redeem", method=RequestMethod.GET)
    public String info(@RequestParam(value="tid") String tid,
    		@RequestParam(value="lat", required = false) double lat,
    		@RequestParam(value="lon", required = false) double lon,
    		@RequestParam(value="iid") String iid,
    		@RequestParam(value="address", required = false) String address,
    		@RequestParam(value="name", required = false) String name,
    		Model model) {
    	
    	model.addAttribute("info", new Info(tid, lat, lon, iid, address, name));
    	return "info";
    }
}