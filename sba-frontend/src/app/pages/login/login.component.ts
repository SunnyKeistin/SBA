import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { saveUserInfo } from '../../utils/utils';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { NzNotificationService } from 'ng-zorro-antd';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  validateForm: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService, 
              private notificationService: NzNotificationService) {}


  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    if (!this.validateForm.valid) {
      return;
    }
    this.userService.login(this.validateForm.value).subscribe(
      res => {
        console.log('res:', res);
        if(res.token) {
          saveUserInfo(res);
        } else if (res.message) {
          this.notificationService.error('Login failed', 'Bad credentials, please try again later.', {nzDuration: 5000});
        }
      }
    );
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }
}
