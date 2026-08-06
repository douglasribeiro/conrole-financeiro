package com.developer.contas.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.contas.dto.EmpresaDTO;
import com.developer.contas.generics.BaseController;
import com.developer.contas.service.EmpresaService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController extends BaseController<EmpresaDTO,	EmpresaService> {

}
