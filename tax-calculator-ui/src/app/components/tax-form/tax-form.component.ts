import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaxCalculationService } from '../../services/tax-calculation.service';
import { TaxCalculationResponse } from '../../models/tax.models';

@Component({
  selector: 'app-tax-form',
  templateUrl: './tax-form.component.html',
  styleUrls: ['./tax-form.component.scss']
})
export class TaxFormComponent {

  @Output() calculated = new EventEmitter<TaxCalculationResponse>();

  form: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private taxService: TaxCalculationService) {
    this.form = this.fb.group({
      grossAnnual: [null, [Validators.required, Validators.min(0)]],
      age: [null, [Validators.required, Validators.min(0), Validators.max(130)]],
      medicalAidMembers: [0, [Validators.min(0), Validators.max(20)]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = null;
    this.taxService.calculate(this.form.value).subscribe({
      next: result => {
        this.loading = false;
        this.calculated.emit(result);
      },
      error: () => {
        this.loading = false;
        this.error = 'Could not reach the tax calculator API. Please ensure the backend is running.';
      }
    });
  }
}
