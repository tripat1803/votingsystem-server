package com.demo.votingapp.controllers.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticatedResponse {
    public String _at;
    public String role;
}
