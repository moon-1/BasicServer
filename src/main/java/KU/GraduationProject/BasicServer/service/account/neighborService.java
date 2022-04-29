package KU.GraduationProject.BasicServer.service.account;

import KU.GraduationProject.BasicServer.domain.entity.account.neighbor;
import KU.GraduationProject.BasicServer.domain.entity.account.user;
import KU.GraduationProject.BasicServer.domain.repository.neighborRepository;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.dto.accountDto.neighborDto;
import KU.GraduationProject.BasicServer.dto.accountDto.nicknameDto;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.util.securityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class neighborService {

    private static final Logger log = LoggerFactory.getLogger(userService.class);

    @Autowired
    private userRepository userRepository;

    @Autowired
    private neighborRepository neighborRepository;

    public ResponseEntity<Object> searchNeighbor(String nickname){

        List<user> userList = userRepository.findByNicknameContaining(nickname);
        List<nicknameDto> nicknameList = new ArrayList<>();

        for(user user : userList){
            nicknameList.add(new nicknameDto(user.getNickname(),user.getUserId()));
        }
        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.NICKNAME_SEARCH_SUCCESS, nicknameList), HttpStatus.OK);
    }

    public ResponseEntity<Object> showNeighborList(){
        List<neighborDto> neighborDtoList = new ArrayList<>();
        user requestUser = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).get();
        List<neighbor> neighborList = neighborRepository.findAllByUser_UserIdAndIsApprove(requestUser.getUserId(),true);
        for(neighbor neighbor : neighborList){
            neighborDtoList.add(new neighborDto(neighbor.getNickname(),neighbor.getNeighborId(),neighbor.getUser().getUserId(),neighbor.isApprove()));
        }
        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.SHOW_NEIGHBOR_LIST,neighborDtoList), HttpStatus.OK);
    }

    public ResponseEntity<Object> showNeighborApplicationList(){
        List<neighborDto> neighborDtoList = new ArrayList<>();
        user requestUser = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).get();
        List<neighbor> neighborList = neighborRepository.findAllByUser_UserIdAndIsApprove(requestUser.getUserId(),false);
        for(neighbor neighbor : neighborList){
            neighborDtoList.add(new neighborDto(neighbor.getNickname(),neighbor.getNeighborId(),neighbor.getUser().getUserId(),neighbor.isApprove()));
        }
        return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.SHOW_NEIGHBOR_LIST,neighborDtoList), HttpStatus.OK);
    }

    public ResponseEntity<Object> requestNeighbor(nicknameDto nicknameDto){

        try{
            user requestUser = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).get();
            neighborRepository.save(new neighbor(requestUser.getNickname(),false,userRepository.getById(nicknameDto.getUserId())));

            return new ResponseEntity(defaultResult.res(statusCode.OK,responseMessage.NEIGHBOR_REQUEST_SUCCESS,
                    "A request was sent to [ " + nicknameDto.getNickname() + " ]"),HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> deleteNeighbor(Long neighborId){
        try{
            if(!neighborRepository.existsById(neighborId)){
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.NOT_FOUND_USER,
                        "neighbor Id : " + neighborId + "is not exists"), HttpStatus.OK);
            }
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
            if(neighborRepository.findById(neighborId).get().getUser() != userInfo.get()) {
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_PROJECT, "Neighbor id :" + neighborId),
                        HttpStatus.OK);
            }
            neighborRepository.deleteById(neighborId);
            return new ResponseEntity(defaultResult.res(statusCode.OK,responseMessage.DELETE_NEIGHBOR),HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<Object> approveNeighbor(neighborDto neighborDto){
        try{
            user user = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).get();
            neighborRepository.setNeighborIsApprove(neighborDto.getNeighborId(),true);
            return new ResponseEntity(defaultResult.res(statusCode.OK,responseMessage.APPROVE_NEIGHBOR_SUCCESS),HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR,
                    responseMessage.INTERNAL_SERVER_ERROR,ex.getMessage()), HttpStatus.OK);
        }
    }
}
