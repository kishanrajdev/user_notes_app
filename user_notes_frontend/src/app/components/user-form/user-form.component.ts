import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserRequest } from '../../models/user.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit, OnDestroy {
  form: FormGroup;
  loading = false;
  submitting = false;
  error: string | null = null;
  userId: string | null = null;
  isEditMode = false;
  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(1)]],
      email: ['', [Validators.required, Validators.email]],
      note: ['']
    });
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(takeUntil(this.destroy$))
      .subscribe((params) => {
        this.userId = params.get('id');
        if (this.userId) {
          this.isEditMode = true;
          this.loadUser(this.userId);
        }
      });
  }

  loadUser(id: string): void {
    this.loading = true;
    this.error = null;
    this.userService.getUserById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (user) => {
          this.form.patchValue({
            name: user.name,
            email: user.email,
            note: user.note || ''
          });
          this.loading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          this.error = 'Failed to load user details.';
          this.loading = false;
          this.cdr.markForCheck();
          console.error(err);
        }
      });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.submitting = true;
    this.error = null;

    const userData: UserRequest = this.form.value;

    const request$ = this.isEditMode && this.userId
      ? this.userService.updateUser(this.userId, userData)
      : this.userService.createUser(userData);

    request$
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.submitting = false;
          this.cdr.markForCheck();
          this.router.navigate(['/users']);
        },
        error: (err) => {
          this.error = this.isEditMode
            ? 'Failed to update user. Please try again.'
            : 'Failed to create user. Please try again.';
          this.submitting = false;
          this.cdr.markForCheck();
          console.error(err);
        }
      });
  }

  get nameError(): string | null {
    const control = this.form.get('name');
    if (control?.hasError('required') && control?.touched) {
      return 'Name is required.';
    }
    if (control?.hasError('minlength') && control?.touched) {
      return 'Name must not be empty.';
    }
    return null;
  }

  get emailError(): string | null {
    const control = this.form.get('email');
    if (control?.hasError('required') && control?.touched) {
      return 'Email is required.';
    }
    if (control?.hasError('email') && control?.touched) {
      return 'Please enter a valid email address.';
    }
    return null;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
