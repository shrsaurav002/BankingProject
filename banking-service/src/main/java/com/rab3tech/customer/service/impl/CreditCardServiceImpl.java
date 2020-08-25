package com.rab3tech.customer.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CreditCardRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.service.CreditCardService;
import com.rab3tech.dao.entity.CreditCardEntity;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.vo.CreditCardVO;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {
	@Autowired
	private CustomerRepository customer;
	@Autowired
	private CreditCardRepository creditRepo;

	@Override
	public byte[] ccFront(String email) {
		CreditCardVO credit = findByEmail(email);
		long num = credit.getCardNumber();
		String numStr = "" + num;
		Date expDate = credit.getExpDate();
		String date = expDate.toString();
		String expDateStr = date.substring(5, 7) + "/" + date.substring(8, 10);
		String name = credit.getName();
		byte[] photo = new byte[] {};
		Resource resouce = new ClassPathResource("images/credit-card-front-template.jpg");
		try {
			InputStream resourceInputStream = resouce.getInputStream();
			Image imgSource = ImageIO.read(resourceInputStream);
			int width = imgSource.getWidth(null);
			int height = imgSource.getHeight(null);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = tag.createGraphics();
			graphics.setBackground(new Color(200, 250, 200));
			graphics.clearRect(0, 0, width, height);
			graphics.setColor(Color.WHITE);
			graphics.drawImage(imgSource, 0, 0, width, height, null);
			graphics.setFont(new Font("Lucida Console", Font.BOLD, 36));
			graphics.drawString(numStr.substring(0, 4), 40, 207);
			graphics.drawString(numStr.substring(4, 8), 150, 207);
			graphics.drawString(numStr.substring(8, 12), 260, 207);
			graphics.drawString(numStr.substring(12, 16), 370, 207);

			graphics.setFont(new Font("Lucida Console", Font.PLAIN, 24));
			graphics.drawString(expDateStr, 65, 250);

			graphics.setFont(new Font("Tahoma", Font.PLAIN, 24));
			graphics.drawString(name, 65, 300);
			;

			graphics.setFont(new Font("Lucida Console", Font.ITALIC, 20));
			graphics.drawString("Master Card", 350, 20);

			Resource logo = new ClassPathResource("images/cb1.png");
			InputStream logoStream = logo.getInputStream();
			Image logoImg = ImageIO.read(logoStream);
			graphics.drawImage(logoImg, 304, 255, 91, 45, null);

			graphics.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", baos);
			baos.flush();
			photo = baos.toByteArray();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return photo;
	}

	@Override
	public byte[] ccBack(String email) {
		CreditCardVO credit = findByEmail(email);
		int secCode = credit.getSecCode();
		byte[] photo = new byte[] {};
		Resource resouce = new ClassPathResource("images/credit-card-back-template.jpg");
		try {
			InputStream resourceInputStream = resouce.getInputStream();
			Image imgSource = ImageIO.read(resourceInputStream);
			int width = imgSource.getWidth(null);
			int height = imgSource.getHeight(null);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = tag.createGraphics();
			graphics.setBackground(new Color(200, 250, 200));
			graphics.clearRect(0, 0, width, height);
			graphics.setColor(Color.black);
			graphics.drawImage(imgSource, 0, 0, width, height, null);
			graphics.setFont(new Font("Lucida Console", Font.BOLD, 28));
			graphics.drawString("" + secCode, 350, 138);

			graphics.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", baos);
			baos.flush();
			photo = baos.toByteArray();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return photo;
	}

	@Override
	public CreditCardVO createNewCreditCard(String email, int id) {
		CreditCardVO creditVO = new CreditCardVO();
		Customer customerE = customer.findById(id).get();
		CreditCardEntity creditEntity = new CreditCardEntity();
		creditEntity.setApr(12.35);
		creditEntity.setCardNumber(creditNumberGenerator());
		creditEntity.setEmail(email);
		creditEntity.setCashbackBonus(0d);
		creditEntity.setCreditScore(650);
		creditEntity.setCurrentBalance(100d);
		creditEntity.setCustomer(customerE);
		creditEntity.setExpDate(generateExpDate());
		creditEntity.setMinPayment(35d);
		creditEntity.setName(customerE.getName());
		creditEntity.setSecCode(secCodeGenerator());
		creditEntity.setStatementBalance(100d);

		BeanUtils.copyProperties(creditEntity, creditVO);
		creditRepo.save(creditEntity);
		return creditVO;
	}

	private int secCodeGenerator() {
		int num = 0;
		while (num < 100 || num > 1000) {
			num = (int) (Math.random() * Math.pow(10, 3));
		}
		return num;
	}

	private long creditNumberGenerator() {
		long num = (long) (Math.random() * Math.pow(10, 16));
		return num;
	}

	private Date generateExpDate() {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.YEAR, 3);
		Date date = ca.getTime();
		return date;
	}

	@Override
	public CreditCardVO findByEmail(String email) {
		CreditCardVO card = new CreditCardVO();
		CreditCardEntity creditEntity = creditRepo.findByEmail(email).get();
		BeanUtils.copyProperties(creditEntity, card);
		return card;
	}

	@Override
	public boolean checkIfExist(String email) {
		boolean exist=false;
		Optional<CreditCardEntity> optional =creditRepo.findByEmail(email);
		if(optional.isPresent()) {
			exist=true;
		}
		return exist;
	}

}
