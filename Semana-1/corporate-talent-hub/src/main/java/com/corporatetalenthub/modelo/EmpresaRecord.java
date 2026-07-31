/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corporatetalenthub.modelo;
    
/**
 * Un Record reduce la verbosidad: Java genera constructor, accesores,
 * equals, hashCode y toString a partir de sus componentes.
 *
 * Sus componentes son inmutables: después de construir el Record no se pueden
 * reasignar. La inmutabilidad es superficial; si un componente fuera un objeto
 * mutable, su contenido aún podría cambiar.
 */
public record EmpresaRecord (
        
        String nombre,
        String nit,
        int anioFundacion)
        {     
}
