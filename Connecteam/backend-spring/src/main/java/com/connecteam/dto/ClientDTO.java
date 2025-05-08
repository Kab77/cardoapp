package com.connecteam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String name;

    @Pattern(regexp = "^[0-9]{14}$", message = "Le numéro SIRET doit contenir 14 chiffres")
    private String siret;

    @Pattern(regexp = "^FR[0-9]{2}[0-9]{9}$", message = "Le numéro de TVA doit être au format FR + 11 chiffres")
    private String tvaNumber;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
    private String streetAddress;

    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir 5 chiffres")
    private String postalCode;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100, message = "Le nom de la ville ne doit pas dépasser 100 caractères")
    private String city;

    @NotBlank(message = "Le nom du contact est obligatoire")
    @Size(max = 100, message = "Le nom du contact ne doit pas dépasser 100 caractères")
    private String contactName;

    @Email(message = "L'adresse email doit être valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    private String contactEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    private String contactPhone;
} 