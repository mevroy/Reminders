/**
 * 
 */
package com.yourpackagename.yourwebproject.scheduler.partitioners;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

/**
 * @author mevan.d.souza
 *
 */
public class RangePartitioner implements Partitioner {

	public static final String limit = "limit";
    public static final String offset = "offset";
    private Logger log = LoggerFactory.getLogger(RangePartitioner.class);
    public int noOfRecordsperPartition;
    
    

	/**
	 * @param noOfRecordsperPartition the noOfRecordsperPartition to set
	 */
	public void setNoOfRecordsperPartition(int noOfRecordsperPartition) {
		this.noOfRecordsperPartition = noOfRecordsperPartition;
	}

	public Map<String, ExecutionContext> partition(int gridSize) {
        final Map<String, ExecutionContext> contextMap = new HashMap<String, ExecutionContext>();

		int range = 20;
		if(noOfRecordsperPartition>0)
		{
			range = noOfRecordsperPartition;
		}
		int fromId = 0;
		int toId = range;

        
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            //context.putInt(offset, fromId);
            //context.putInt(limit, toId);
            context.putInt("partioner.read.count", fromId);
            context.putInt("partioner.read.count.max", toId);
            
            context.putString("ThreadName", "Thread:"+i);
            contextMap.put(getContextName(i), context);
            log.info("Created Partion :"+getContextName(i)+" with offset "+fromId+" and limit "+toId);
            fromId+=range;
            toId+=range;
        }

        return contextMap;
    }

    private String getContextName(int index) {
        return String.format("partition-%d", index);
    }
}
