package com.scipionyx.butterflyeffect.api.infrastructure.services.server.controller;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.scipionyx.butterflyeffect.api.infrastructure.services.server.IRepositoryService;

/**
 * 
 * @author Renato Mendes
 *
 * @param <T>
 *            Service
 * @param <E>
 *            Entity
 */
public abstract class AbstractElasticsearchRestController<T extends IRepositoryService<ENTITY>, ENTITY>
		extends AbstractRestController<IRepositoryService<ENTITY>, ENTITY> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractElasticsearchRestController.class);

	private Class<ENTITY> entityClazz;

	@SuppressWarnings("unused")
	private Class<ENTITY[]> arrayClazz;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		// This code obtains what is the class that was provided as generic
		// parameter
		entityClazz = (Class<ENTITY>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
		// clazz.getComponentType().getArr
		arrayClazz = (Class<ENTITY[]>) Array.newInstance(entityClazz, 0).getClass();
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @throws RestClientException
	 */
	@RequestMapping(path = "/findAll", method = { RequestMethod.GET, RequestMethod.POST })
	public final ResponseEntity<Iterable<ENTITY>> findAll() throws RestClientException, Exception {
		LOGGER.debug("Health request");
		CrudRepository<ENTITY, Long> repository = service.getRepository();
		return (new ResponseEntity<>(repository.findAll(), HttpStatus.OK));
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @throws RestClientException
	 */
	@RequestMapping(path = "/findAllOrderBy", method = { RequestMethod.GET })
	public final ResponseEntity<List<ENTITY>> findAllOrderBy(String orderBy) throws RestClientException, Exception {
		LOGGER.debug("findAllOrderBy");
		return (new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));

	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @throws RestClientException
	 */
	@RequestMapping(path = "/findAllByOrderBy", method = { RequestMethod.PUT })
	public final ResponseEntity<List<ENTITY>> findAllByOrderBy(
			@RequestBody(required = true) Map<String, Object> parameters, HttpServletRequest request)
			throws RestClientException, Exception {
		LOGGER.debug("findAllByOrderBy");
		return (new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 * @throws RestClientException
	 */
	@RequestMapping(path = "/save", method = { RequestMethod.PUT })
	public final ResponseEntity<ENTITY> save(@RequestBody(required = true) ENTITY entity)
			throws RestClientException, Exception {
		LOGGER.debug("save");
		CrudRepository<ENTITY, Long> repository = service.getRepository();
		ENTITY persisted;
		try {
			persisted = repository.save(entity);
			return (new ResponseEntity<>(persisted, HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			return (new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RestClientException
	 * @throws Exception
	 */
	@RequestMapping(path = "/delete", method = { RequestMethod.DELETE })
	public final ResponseEntity<String> delete(@RequestParam(required = true) Long id)
			throws RestClientException, Exception {
		LOGGER.debug("delete, paramId=", id);
		CrudRepository<ENTITY, Long> repository = service.getRepository();
		try {
			repository.delete(id);
			return (new ResponseEntity<>("Ok", HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			return (new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RestClientException
	 * @throws Exception
	 */
	@RequestMapping(path = "/findOne/{id}", method = { RequestMethod.GET })
	public final ResponseEntity<ENTITY> findOne(@PathVariable Long id) throws RestClientException, Exception {
		LOGGER.debug("findOne, paramId=", id);
		CrudRepository<ENTITY, Long> repository = service.getRepository();
		try {
			return (new ResponseEntity<>(repository.findOne(id), HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			return (new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
		}
	}

}