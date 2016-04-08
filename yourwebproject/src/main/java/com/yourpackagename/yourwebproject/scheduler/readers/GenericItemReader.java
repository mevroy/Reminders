package com.yourpackagename.yourwebproject.scheduler.readers;

import java.util.List;
import java.util.Properties;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.service.GroupMembersService;

@Component("reader")
@Scope("step")
public class GenericItemReader implements ItemReader<GroupMember> {

	/** Logger for this class. */
	private static final Logger logger = LoggerFactory
			.getLogger(GenericItemReader.class);

	@Autowired
	private GroupMembersService groupMembersService;

	@Value("#{jobParameters['runId']}")
	private String runId;

	private List<GroupMember> gms = groupMembersService.findByGroupCode("MKC");
	/**
	 * Read GroupMember.
	 * 
	 * @return the message
	 * @throws Exception
	 *             the exception
	 * @throws UnexpectedInputException
	 *             the unexpected input exception
	 * @throws ParseException
	 *             the parse exception
	 * @throws NonTransientResourceException
	 *             the non transient resource exception
	 */
	public GroupMember read() throws MessagingException,
			UnexpectedInputException, ParseException,
			NonTransientResourceException {

		logger.trace("read() : enter");
		logger.info("Entering Read");
		GroupMember gm = null;
		if(!gms.isEmpty())
	gm = 	gms.remove(0);
		logger.info("Exiting Read");
		logger.trace("read() : exit");
		return gm;
	}

	/*
	 * protected Message getMessage() throws MessagingException {
	 * logger.trace("getMessage() : enter");
	 * 
	 * Message message = null; Message[] messages = null; Folder inbox = null;
	 * Store store = null;
	 * 
	 * try { Properties props = System.getProperties();
	 * props.setProperty(storeProtocol, protocol); if(!strictAuthentication) {
	 * props.put("mail.imaps.auth.plain.disable", "true");
	 * props.put("mail.imaps.auth.ntlm.disable", "true");
	 * props.put("mail.imaps.auth.gssapi.disable", "true"); }
	 * 
	 * // Establishing session with email server Session session =
	 * Session.getDefaultInstance(props, null); store =
	 * session.getStore(protocol); if(!store.isConnected())
	 * store.connect(serverHost, userName, password);
	 * 
	 * // Getting inbox folder inbox = store.getFolder(folder);
	 * inbox.open(Folder.READ_WRITE);
	 * 
	 * //search if(unreadOnly) messages = inbox.search(new FlagTerm(new
	 * Flags(Flags.Flag.SEEN), false)); else messages = inbox.getMessages();
	 * 
	 * if(messages!=null && messages.length>0) { message = messages[0];
	 * message.setFlag(Flag.SEEN, true); } } catch (MessagingException ex) {
	 * String msg = "Cannot process EmailItemReader.getMessage \n " +
	 * ex.getMessage(); logger.error(msg.toString(), ex);
	 * if(store.isConnected()) { try { store.close(); } catch(Exception e) {} }
	 * throw ex; }
	 * 
	 * logger.trace("getMessage() : exit"); return message; }
	 */
}