package com.sr.roboticrover.resource;

import com.sr.roboticrover.service.ControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class Controller {

    private final ControllerService controllerService;

    public Controller(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    /**
     * this method is used to defined the plateau grid size and we used this to validate robotic rovers movement path
     * and by default i have set this as 5-5 grid as per the test input
     *
     * @param x this variable is used to define grid x axis
     * @param y this variable is used to define grid y axis
     */
    @GetMapping("define-grid/{x}/{y}")
    public void makeGrid(@PathVariable byte x, @PathVariable byte y) {
        this.controllerService.defineGridSize(x, y);
    }

    /**
     * this api is used to defined the robotic rovers position
     *
     * @param x         is the position that robotic rover located vertically
     * @param y         is the position that robotic rover located horizontally
     * @param direction is the cardinal direction that robotic rover faced
     *                  for this we define only 4 main direction N("North"), E("East"), S("South"), W("West")
     * @return ResponseEntity
     */
    @GetMapping("define-position/{x}/{y}/{direction}")
    public ResponseEntity<String> definePosition(@PathVariable byte x, @PathVariable byte y, @PathVariable char direction) {
        return this.controllerService.definePosition(x, y, direction);
    }

    /**
     *  this API is used to move the robotic through the plateau grid
     * @param direction expecting string with possible chars L("Left"), R("Right"), M("Move Forward")
     * @return ResponseEntity
     */
    @GetMapping("move/{direction}")
    public ResponseEntity<String> move(@PathVariable String direction) {
        return this.controllerService.move(direction);
    }
}
