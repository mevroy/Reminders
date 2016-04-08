package com.yourpackagename.yourwebproject.scheduler.writers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;

@Component("writer")
public class GenericItemWriter implements ItemStreamWriter<GroupEmail> {
	protected @Autowired GroupEmailsService groupEmailsService;
	private static final Logger logger = LoggerFactory
			.getLogger(GenericItemWriter.class);
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends GroupEmail> emailList) {
		for(GroupEmail groupEmail: emailList)
		{
				groupEmail.setUpdatedAt(Calendar.getInstance().getTime());
				try {
					groupEmailsService.insertOrUpdate(groupEmail);
					logger.info("Updated Object:"+groupEmail.getGroupEmailId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//	}
		}
	}

	public void close() throws ItemStreamException {
		// TODO Auto-generated method stub
		
	}

	public void open(ExecutionContext arg0) throws ItemStreamException {
		// TODO Auto-generated method stub
		
	}

	public void update(ExecutionContext arg0) throws ItemStreamException {
		// TODO Auto-generated method stub
		
	}
}
