package com.resumo.receptor_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumo.receptor_service.entity.Usuario;
import com.resumo.receptor_service.repository.UsuarioRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioConsumer {

    @Autowired
    private UsuarioRepository repository;

    @RabbitListener(queues = "fila.usuarios")
    public void consumirUsuario(String usuarioJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);
            repository.save(usuario);
            System.out.println("Usuário salvo no banco: " + usuario);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
