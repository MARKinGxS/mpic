package priv.markingxs.mpic.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

@Slf4j
public class SerializeUtil {

    //序列化
    public static byte[] serialize(Object object){

        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        }catch (Exception e){
            log.error("["+new Date()+"]-- serialize object fail");
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object deserialize(byte[] bytes){
        ByteArrayInputStream byteArrayInputStream = null;
        try{
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        }catch (Exception e){
            log.error("["+new Date()+"]-- deserialize object fail");
            e.printStackTrace();
        }
        return null;
    }
}
