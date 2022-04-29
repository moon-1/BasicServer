package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.accountDto.neighborDto;
import KU.GraduationProject.BasicServer.dto.accountDto.nicknameDto;
import KU.GraduationProject.BasicServer.service.account.neighborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/neighbor")
public class neighborController {

    @Autowired
    private neighborService neighborService;

    @GetMapping("/{nickname}/search")
    public ResponseEntity<Object> search(@PathVariable String nickname) {
        return neighborService.searchNeighbor(nickname);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> showNeighborList(){
        return neighborService.showNeighborList();
    }

    @GetMapping("/application/list")
    public ResponseEntity<Object>  showNeighborApplicationList(){
        return neighborService.showNeighborApplicationList();
    }

    @PostMapping("/request")
    public ResponseEntity<Object> requestNeighbor(@RequestBody nicknameDto nicknameDto){
        return neighborService.requestNeighbor(nicknameDto);
    }

    @DeleteMapping("/{neighborId}/delete")
    public ResponseEntity<Object> deleteNeighbor(@PathVariable Long neighborId){
        return neighborService.deleteNeighbor(neighborId);
    }

    @PostMapping("/approve")
    public ResponseEntity<Object> approveNeighbor(@RequestBody neighborDto neighborDto){
        return neighborService.approveNeighbor(neighborDto);
    }

}
