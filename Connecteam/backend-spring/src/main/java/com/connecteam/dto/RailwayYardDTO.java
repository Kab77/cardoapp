package com.connecteam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RailwayYardDTO {
    private Long id;

    @NotBlank(message = "Le nom du faisceau est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String name;

    @NotBlank(message = "La zone est obligatoire")
    private String zone;

    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    private String description;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
    private String streetAddress;

    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir 5 chiffres")
    private String postalCode;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100, message = "Le nom de la ville ne doit pas dépasser 100 caractères")
    private String city;

    @Size(max = 1000, message = "Les instructions d'accès ne doivent pas dépasser 1000 caractères")
    private String accessInstructions;

    @Size(max = 1000, message = "Les instructions de stationnement ne doivent pas dépasser 1000 caractères")
    private String parkingInstructions;

    private Long installationId;
} 