package com.testNutech.ApiProgrammer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testNutech.ApiProgrammer.config.JwtTokenUtil;
import com.testNutech.ApiProgrammer.dto.HistoryTransactionDto;
import com.testNutech.ApiProgrammer.dto.UsersBalanceDto;
import com.testNutech.ApiProgrammer.exception.BalanceKurang;
import com.testNutech.ApiProgrammer.exception.invalidTopUp;
import com.testNutech.ApiProgrammer.model.HistoryTransaction;
import com.testNutech.ApiProgrammer.model.Service;
import com.testNutech.ApiProgrammer.model.Transaction;
import com.testNutech.ApiProgrammer.model.User;
import com.testNutech.ApiProgrammer.model.UsersBalance;
import com.testNutech.ApiProgrammer.repository.HistoryTransactionRepository;
import com.testNutech.ApiProgrammer.repository.TransactionRepository;
import com.testNutech.ApiProgrammer.repository.UserRepository;
import com.testNutech.ApiProgrammer.repository.UsersBalanceRepository;
import com.testNutech.ApiProgrammer.request.TransactionReq;
import com.testNutech.ApiProgrammer.response.HttpResponse;
import com.testNutech.ApiProgrammer.response.InfoBalance;
import com.testNutech.ApiProgrammer.service.ServiceService;
import com.testNutech.ApiProgrammer.service.UsersBalanceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Module Transaction")
public class TransactionController {
	
	@Autowired
	private UsersBalanceService usersBalanceService;
	
	@Autowired
	private UsersBalanceRepository userBalanceRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private HistoryTransactionRepository historyTransRepo;
	
	@Autowired
	private TransactionRepository transRepo;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@CrossOrigin()
	@PostMapping("/topup")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<UsersBalanceDto> topUpBalance(@RequestBody UsersBalanceDto userBalance, HttpServletRequest request){
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		
		HttpResponse<UsersBalanceDto> result = new HttpResponse<UsersBalanceDto>();
		
		if (userBalance.getTop_up_amount() >= 0) {
			UsersBalance data = usersBalanceService.getUsersBalance(user.getId());
			
			if (data != null) {
				Integer previousBalance = data.getBalance();
				UsersBalance balance = UsersBalance.builder()
						.id(data.getId())
						.userId(user.getId())
						.balance(userBalance.getTop_up_amount() + previousBalance)
						.build();
				
				userBalanceRepo.save(balance);
				
			} else {
				UsersBalance balance = UsersBalance.builder()
						.userId(user.getId())
						.balance(userBalance.getTop_up_amount())
						.build();
				
				userBalanceRepo.save(balance);
			}
			
			
			result.setStatus(0);
			result.setMessage("Top Up Balance berhasil");
			result.setData(userBalance);
			
		} else {
			throw new invalidTopUp();
		}
		return result;
	}
	
	@CrossOrigin()
	@GetMapping("/balance")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<InfoBalance> getBalance(HttpServletRequest request){
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		
		HttpResponse<InfoBalance> result = new HttpResponse<InfoBalance>();
		
		UsersBalance data = usersBalanceService.getUsersBalance(user.getId());
		
		if (data != null) {
			InfoBalance balance = InfoBalance.builder()
					.balance(data.getBalance())
					.build();
			
			result.setStatus(0);
			result.setMessage("Get Balance Berhasi");
			result.setData(balance);
		}
		
		return result;
	}
	
	@CrossOrigin()
	@PostMapping("/transaction")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<Transaction> transaction(@RequestBody TransactionReq transReq, HttpServletRequest request) throws Exception{
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = currentDateTime.format(formatDate);
		
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		UsersBalance balance = usersBalanceService.getUsersBalance(user.getId());
		
		HttpResponse<Transaction> result = new HttpResponse<Transaction>();
		
		Service service = serviceService.findByServiceCode(transReq.getService_code());
		
		if(balance.getBalance() != null) {
			
			Transaction transaction = Transaction.builder()
					.user_id(user.getId())
					.service_code(service.getServiceCode())
					.service_name(service.getServiceName())
					.transaction_type("PAYMENT")
					.total_amount(service.getServiceTarif())
					.created_on(dateTime)
					.build();
			
			transRepo.save(transaction);
			
			result.setStatus(0);
			result.setMessage("Transaksi berhasil");
			result.setData(transRepo.findTransId(user.getId()));
			
			String invoice = transRepo.findInvoice(user.getId());
			
			//Setelah melakukan transaksi maka akan ditambahkan ke tabel history_transaksi
			HistoryTransaction hisTransaction = HistoryTransaction.builder()
					.invoice_number(invoice)
					.user_id(user.getId())
					.description(service.getServiceName())
					.transaction_type("PAYMENT")
					.total_amount(service.getServiceTarif())
					.created_on(dateTime)
					.build();
			
			historyTransRepo.save(hisTransaction);
			
			//setelah melakuakan penambahan pada tabel transaksi maka pada tabel transaksi akan dihapus
			transRepo.deleteById(transRepo.findId(user.getId()));
			
			//dibawah adalah program untuk updateSaldo ketika user melakukan transaksi
			UsersBalance data = usersBalanceService.getUsersBalance(user.getId());
			Integer previousBalance = data.getBalance();
			UsersBalance updateBalance = UsersBalance.builder()
					.id(data.getId())
					.userId(user.getId())
					.balance(previousBalance - service.getServiceTarif())
					.build();
			
			userBalanceRepo.save(updateBalance);
			
		} else {
			throw new BalanceKurang();
 		}
		
		return result;
		
		
		
	}
	
	@CrossOrigin()
	@GetMapping("/transaction/history")
	@SecurityRequirement(name = "Tokenbearer")
	public HttpResponse<List<HistoryTransactionDto>> getHistoryTrans(@RequestParam Integer offset, @RequestParam Integer limit, HttpServletRequest request){
		String jwtToken = getToken(request);
		User user = userRepo.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
		
		HttpResponse<List<HistoryTransactionDto>> result = new HttpResponse<List<HistoryTransactionDto>>();
		
		List<HistoryTransactionDto> listHistory = new ArrayList<HistoryTransactionDto>();
		List<HistoryTransaction> historyTrans =  historyTransRepo.listHistoryTransWithSpecificUserId(user.getId(), limit, offset);
		
		for (HistoryTransaction h : historyTrans) {
			HistoryTransactionDto data = HistoryTransactionDto.builder()
					.invoice_number(h.getInvoice_number())
					.transaction_type(h.getTransaction_type())
					.description(h.getDescription())
					.total_amount(h.getTotal_amount())
					.created_on(h.getCreated_on())
					.build();
			
			listHistory.add(data);
		}
		
		result.setStatus(0);
		result.setMessage("Transaksi berhasil");
		result.setData(listHistory);
		
		return result;
		
		
	}
	
	private String getToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String jwtToken = authorizationHeader.substring(7);
		
		return jwtToken;
	}
}
