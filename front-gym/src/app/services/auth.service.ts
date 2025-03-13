import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private cookieService: CookieService, private router: Router) { }

  methodAuth() {
    if (!this.cookieService.check('gym_token')) {
      this.router.navigate(['/login']);
    }
  }

  getUserId() {
    return this.cookieService.get('gym_user_id');
  }

  getEstado() {
    return this.cookieService.get('gym_estado');
  }

  getRol() {
    return this.cookieService.get('gym_rol');
  }

  getNombre() {
    return this.cookieService.get('gym_nombre');
  }

  getToken() {
    return this.cookieService.get('gym_token');
  }

  methodLogout() {
    swal.fire({
      position: 'center',
      title: '¿Quieres cerrar Sesión?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'NO',
      confirmButtonText: 'SI'
    }).then(async (result) => {
      if (result.isConfirmed) {
        this.cookieService.deleteAll();
        window.location.href = "/login"
      }
    });
  }

}
