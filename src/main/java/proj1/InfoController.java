package proj1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfoController {

    @RequestMapping(value="/redeem", method=RequestMethod.GET)
    public String info(@RequestParam(value="tid") long tid,
    		@RequestParam(value="lat") double lat,
    		@RequestParam(value="lat") double lon,
    		Model model) {
    	
    	model.addAttribute("info", new Info(tid, lat, lon));
    	return "info";
    }
}