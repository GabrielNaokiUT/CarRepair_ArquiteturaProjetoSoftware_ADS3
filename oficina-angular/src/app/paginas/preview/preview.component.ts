import { Component } from '@angular/core';

@Component({
  selector: 'app-preview',
  standalone: true,
  template: `<iframe src="/assets/carrepair-preview.html"></iframe>`,
  styleUrl: './preview.component.css'
})
export class PreviewComponent {}
