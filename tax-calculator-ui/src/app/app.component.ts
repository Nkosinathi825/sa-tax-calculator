import { Component } from '@angular/core';
import { TaxCalculationResponse } from './models/tax.models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  result: TaxCalculationResponse | null = null;

  onCalculated(result: TaxCalculationResponse): void {
    this.result = result;
  }
}
