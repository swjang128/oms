/*
 * package com.oms.api;
 * 
 * import java.io.IOException; import java.util.ArrayList; import
 * java.util.HashMap; import java.util.List; import java.util.Map;
 * 
 * import javax.validation.Valid;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.oms.config.ResponseCode; import com.oms.dto.LawDTO; import
 * com.oms.entity.Law; import com.oms.service.LawService;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 *//**
	 * Law 서비스에 대한 Rest API
	 * 
	 * @author capias J
	 *
	 */
/*
 * @RestController
 * 
 * @Slf4j
 * 
 * @RequestMapping("api") public class LawAPI {
 * 
 * @Autowired LawService lawService;
 * 
 *//**
	 * 법안 목록 조회
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
/*
 * @GetMapping("law") public Map<String, Object> read() { // 기본 변수 설정
 * Map<String, Object> resultMap = new HashMap<String, Object>(); List<LawDTO>
 * lawList = new ArrayList<LawDTO>(); int status =
 * ResponseCode.Status.ERROR_ABORT; String message =
 * ResponseCode.Message.ERROR_ABORT;
 * 
 * // 법안 목록 조회 try { lawList = lawService.read(); status =
 * ResponseCode.Status.OK; message = ResponseCode.Message.OK; } catch (Exception
 * e) { e.printStackTrace(); }
 * 
 * // RESTful API 결과를 리턴 resultMap.put("lawList", lawList);
 * resultMap.put("message", message); resultMap.put("status", status);
 * 
 * return resultMap; }
 * 
 *//**
	 * 법안 등록 (CREATE)
	 * 
	 * @param RequestBody
	 * @throws IOException
	 */
/*
 * @PostMapping("law") public Map<String, Object> create(@Valid @RequestBody
 * LawDTO lawDTO) { // 기본 변수 설정 Map<String, Object> resultMap = new
 * HashMap<String, Object>(); int status = ResponseCode.Status.ERROR_ABORT;
 * String message = ResponseCode.Message.ERROR_ABORT; Law result = null;
 * 
 * // 법안 등록 result = lawService.create(lawDTO); if (result != null) { status =
 * ResponseCode.Status.CREATED; message = ResponseCode.Message.CREATED; }
 * 
 * // RESTful API 결과를 리턴 resultMap.put("result", result);
 * resultMap.put("status", status); resultMap.put("message", message);
 * 
 * return resultMap; }
 * 
 *//**
	 * 법안 삭제 (DELETE)
	 * 
	 * @param PathVariable
	 * @return
	 */
/*
 * @DeleteMapping("law/{id}") public Map<String, Object>
 * delete(@PathVariable("id") List<Long> param) {
 * log.info("**** delete called"); // 기본 변수 설정 Map<String, Object> resultMap =
 * new HashMap<String, Object>(); int result = 0; int status =
 * ResponseCode.Status.NOT_FOUND; String message =
 * ResponseCode.Message.NOT_FOUND;
 * 
 * // 법안 삭제 try { result = lawService.delete(param); if (result == 1) { status =
 * ResponseCode.Status.OK; message = ResponseCode.Message.OK; } } catch
 * (Exception e) { e.printStackTrace(); }
 * 
 * // RESTful API 결과를 리턴 resultMap.put("status", status);
 * resultMap.put("message", message);
 * 
 * return resultMap; }
 * 
 *//**
	 * 법안 수정 (UPDATE)
	 * 
	 * @param RequestBody
	 * @return
	 *//*
		 * @PutMapping("law") public Map<String, Object> update(@Valid @RequestBody
		 * LawDTO lawDTO) { // 기본 변수 설정 Map<String, Object> resultMap = new
		 * HashMap<String, Object>(); int result = 0; int status =
		 * ResponseCode.Status.ERROR_ABORT; String message =
		 * ResponseCode.Message.ERROR_ABORT; // Long id = payload.getId();
		 * 
		 * // 법안 수정 try { result = lawService.update(lawDTO); if (result == 1) { status
		 * = ResponseCode.Status.OK; message = ResponseCode.Message.OK; } } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 * // RESTful API 결과를 리턴 resultMap.put("status", status);
		 * resultMap.put("message", message); // resultMap.put("id", id);
		 * 
		 * return resultMap; }
		 * 
		 * }
		 */