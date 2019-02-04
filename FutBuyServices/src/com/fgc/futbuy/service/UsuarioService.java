package com.fgc.futbuy.service;



import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.exceptions.MailException;
import com.fgc.futbuy.model.Direccion;
import com.fgc.futbuy.model.Usuario;

public interface UsuarioService {

	public Usuario findById(Integer id) throws InstanceNotFoundException, DataException;
	
	public Usuario login (String email, String password) throws DataException;
	
	public Boolean exists(Integer id) throws DataException;
	
	public Usuario create(Usuario u) throws DuplicateInstanceException, DataException, MailException;
	
	public void update(Usuario u) throws InstanceNotFoundException, DataException;
	
	public Integer delete(Integer id) throws InstanceNotFoundException, DataException;
	
	public void editDireccion(Direccion direccion, Usuario u) throws InstanceNotFoundException, DataException;
	
	public Usuario findByEmailToRecovery (String email) throws MailException, DataException;
	
	public void setCodigo (Usuario e) throws MailException, DataException;

	public void cambiarContra (Integer codigo, String email, String psswd) throws DataException;
}
