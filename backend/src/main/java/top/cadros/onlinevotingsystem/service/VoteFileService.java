package top.cadros.onlinevotingsystem.service;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.springframework.stereotype.Component;

import top.cadros.onlinevotingsystem.object.*;

/**
 * <p>问卷文件服务类</p>
 * <p>用于将问卷对象写入文件，从文件中读取问卷对象，删除问卷文件</p>
 * @author 高洪森
 */
@Component
public class VoteFileService {
    private static ObjectOutputStream oos;

    public static void outputVoteToFile(Vote vote) throws Exception{
        File directory = new File(".\\src\\main\\resources\\voteFiles\\"+vote.getVote_id()+".dat");
        if (directory.exists()){
            directory.delete(); // 删除文件
        }else{
            directory.createNewFile(); // 创建文件
        }
        oos=new ObjectOutputStream(new FileOutputStream(directory));
        oos.writeObject(vote);
        oos.close();
    }

    public static Vote readVoteFromFile(int vote_id) throws Exception{
        File file=new File(".\\src\\main\\resources\\voteFiles\\"+vote_id+".dat");
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file))){
            return (Vote)ois.readObject();
        }
    }

    public static void deleteVoteFile(int vote_id) throws Exception{
        File file=new File(".\\src\\main\\resources\\voteFiles\\"+vote_id+".dat");
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        file.delete();
    }
}