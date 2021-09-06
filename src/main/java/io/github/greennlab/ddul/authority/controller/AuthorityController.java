package io.github.greennlab.ddul.authority.controller;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulAuthorityController")
@RequestMapping("_authority")
@RequiredArgsConstructor
public class AuthorityController {

  private final DDulAuthorityRepository repository;

  @GetMapping
  public AuthorityOutputDTO getAuthority(Long id) {
    final Optional<Authority> root = repository.findById(id);
    return AuthorityOutputDTO.mapped.to(root.orElse(null));
  }

}
