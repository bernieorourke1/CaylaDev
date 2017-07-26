package bor.cayladev;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import android.content.Context;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;


/**
 * Created by bernadette on 20/07/2017.
 */

public class MasterClass {

    CognitoCachingCredentialsProvider credentialsProvider=null;
    CognitoSyncManager syncManager=null;
    AmazonS3Client s3Client=null;
    TransferUtility transferUtility=null;


    public CognitoCachingCredentialsProvider getcredentials(Context context){
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:8e6c2839-0375-482f-9b24-ad0dd07f434b", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        syncManager=new CognitoSyncManager(context,Regions.US_EAST_1,credentialsProvider);
        Dataset dataset=syncManager.openOrCreateDataset("Mydataset");
        dataset.put("mykey","myvlaue");

        dataset.synchronize(new DefaultSyncCallback());

        return credentialsProvider;
    }
public AmazonS3Client inits3client(Context context){
    if(credentialsProvider==null){
        getcredentials(context);
            s3Client = new AmazonS3Client(credentialsProvider);
            s3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
    }
    return s3Client;
}
public TransferUtility checktransferutility(AmazonS3Client s3Client, Context context){
    if(transferUtility==null){
       transferUtility =new TransferUtility(s3Client, context);
    }else {
        return transferUtility;
    }
    return transferUtility;
}

}
