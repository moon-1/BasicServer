package KU.GraduationProject.BasicServer.domain.repository.floorPlan;

import KU.GraduationProject.BasicServer.Interface.Repository.FloorPlan.floorPlanSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class floorPlanSearchRepository implements floorPlanSearchRepositoryImpl {

    private static String rootPath = "/Users/moon/Desktop/FloorPlan";

    private static final Map<String,List<String>> floorPlansPath = new ConcurrentHashMap<>();

    public List<String> cityListProvider(){
        List<String> directoryList = new ArrayList<>();
        File[] directories = new File(rootPath).listFiles(File::isDirectory);
        for(File directory : directories){
            directoryList.add(directory.getName());
        }
        save(rootPath,directoryList);
        return findByPath(rootPath);
    }

    public List<String> floorPlanListProvider(String city){
        String path = rootPath + "/" + city;
        List<String> floorPlanList = new ArrayList<>();
        File[] files = new File(path).listFiles(File::isFile);
        for(File file : files){
            floorPlanList.add(file.getName());
        }
        save(path, floorPlanList);
        return findByPath(path);
    }

    public String floorPlanJsonFileReader(String city,String fileName){
        String contents = "";
        try{
            Path path = Paths.get(filePathProvider(city,fileName));
            contents = Files.readString(path);
            contents = base64DataReader(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    private List<String> findByPath(String path){
        return floorPlansPath.get(path);
    }

    private String filePathProvider(String city,String fileName){
            return rootPath + "/" + city + "/" + fileName + ".json";
    }

    private Map<String,List<String>> save(String path, List<String> contentsList){
        floorPlansPath.put(path,contentsList);
        return floorPlansPath;
    }

    private String base64DataReader(String floorPlanJson){
        String[] json = floorPlanJson.split("'");
        floorPlanJson = json[1];
        return floorPlanJson;
    }

    public void deleteAll(){ floorPlansPath.clear();}

}
