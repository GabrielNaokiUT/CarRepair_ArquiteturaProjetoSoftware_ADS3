import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

import { MensagensComponent } from './shared/mensagens/mensagens.component';

@Component({
  selector: 'app-root',
  imports: [NgIf, RouterOutlet, RouterLink, RouterLinkActive, MensagensComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  readonly titulo = 'Oficina Acadêmica';

  private readonly router = inject(Router);

  get isPreview(): boolean {
    return this.router.url.startsWith('/preview');
  }
}
