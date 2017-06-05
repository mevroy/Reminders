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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;









import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;

@Component("emailWriter")
public class EmailOutItemWriter implements ItemStreamWriter<GroupEmail> {
	protected @Autowired GroupEmailsService groupEmailsService;
	protected @Autowired GroupEmailActivityService groupEmailActivityService;
	private static final Logger logger = LoggerFactory
			.getLogger(EmailOutItemWriter.class);
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends GroupEmail> emailList) {

		for(GroupEmail groupEmail: emailList)
		{
				groupEmail.setUpdatedAt(Calendar.getInstance().getTime());
				try {

					groupEmailsService.update(groupEmail);
					logger.info("Updated Object:"+groupEmail.getGroupEmailId());
					GroupEmailActivity groupEmailActivity = new GroupEmailActivity();
					groupEmailActivity.setEmailActivity(EmailActivity.UPDATE);
					groupEmailActivity.setActivityTime(groupEmail.getUpdatedAt());
					groupEmailActivity.setActivityBy(groupEmail.getUpdatedBy());
					groupEmailActivity.setGroupEmail(groupEmail);
					groupEmailActivityService.insert(groupEmailActivity);
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
