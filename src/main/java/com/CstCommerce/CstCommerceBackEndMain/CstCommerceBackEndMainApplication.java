package com.CstCommerce.CstCommerceBackEndMain;

import com.CstCommerce.CstCommerceBackEndMain.entity.ShopAndProduct.*;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Basket;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Bill;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
@EnableTransactionManagement
public class CstCommerceBackEndMainApplication /* implements CommandLineRunner */ {

  public static void main(String[] args) {
    SpringApplication.run(CstCommerceBackEndMainApplication.class, args);
  }

	/*

	private final ShopConnectProductRepository shopConnectProductRepository;

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	private final BasketRepository basketRepository;

	private final BillRepository billRepository;

	private  final ShopRepository shopRepository;

	private final VoteRepository voteRepository;

	@Override
	public void run (String...args) throws Exception{
		// admin //
		Users admin = new Users(
						"huongAdmin",
						"caobahuongadmin@gmail.com",
						"$2a$10$agm32HCdIkOrdYFdkbMSZ.ISr4ZIb6YBDdFTbSTrdxaDbrerTM7sW",
						"0946483158",
						"admin"
		);
		userRepository.save(admin);

		// owner //

		Users owner = new Users(
						"huongShop",
						"caobahuongshop@gmail.com",
						"$2a$10$agm32HCdIkOrdYFdkbMSZ.ISr4ZIb6YBDdFTbSTrdxaDbrerTM7sW",
						"0946483158",
						"Owner"
		);
		userRepository.save(owner);

		Basket basketOwner = new Basket();
		basketOwner.setUsers(owner);
		basketRepository.save(basketOwner);

		Bill billOwner = new Bill();
		billOwner.setUsers(owner);
		billRepository.save(billOwner);

		Shop shopOwner = new Shop();
		shopOwner.setUsers(owner);
		shopOwner.setShopType(ShopType.WORK);
		shopOwner.setRegistrationDate(Instant.now());
		shopOwner.setShopName("huong-shop");
		shopRepository.save(shopOwner);

		Product thit = new Product(
						"thit",
						10L,
						"an rat ngon",
						10,
						"work"
		);
		productRepository.save(thit);


		Vote vote = new Vote();
		vote.setProduct(thit);
		voteRepository.save(vote);
		ShopConnectProduct shopConnectThit = new ShopConnectProduct(shopOwner, thit);
		shopConnectProductRepository.save(shopConnectThit);



		Product rau = new Product(
						"rau",
						10L,
						"rau rat tuoi",
						15,
						"work"
		);
		productRepository.save(rau);


		Vote vote1 = new Vote();
		vote1.setProduct(rau);
		voteRepository.save(vote1);

		ShopConnectProduct shopConnectRau = new ShopConnectProduct(shopOwner, rau);
		shopConnectProductRepository.save(shopConnectRau);


		// User //

		Users users = new Users(
						"huong",
						"caobahuong@gmail.com",
						"$2a$10$agm32HCdIkOrdYFdkbMSZ.ISr4ZIb6YBDdFTbSTrdxaDbrerTM7sW",
						"0988748036",
						"User"
		);
		userRepository.save(users);

		Basket basket = new Basket();
		basket.setUsers(users);
		basketRepository.save(basket);

		Bill bill = new Bill();
		bill.setUsers(users);
		billRepository.save(bill);

	}

	 */
}
