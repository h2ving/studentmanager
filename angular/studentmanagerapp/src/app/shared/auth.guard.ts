import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { AuthService } from '../services/auth.service';

import { JwtHelperService } from '@auth0/angular-jwt';
import { NotificationService } from '../services/notification.service';
import { HttpClient, HttpContext } from '@angular/common/http';
import { BYPASS_ACCESS_TOKEN } from '../interceptors/auth.interceptor';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  public jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private authService: AuthService, public router: Router, public notificationService: NotificationService, private http: HttpClient) { }

  async canActivate(route: ActivatedRouteSnapshot) {
    const userRole = sessionStorage.getItem('Role');
    const redirectURI = sessionStorage.getItem('RedirectURI');
    const accessToken = this.authService.getAccessToken();
    const refreshToken = this.authService.getRefreshToken();
    const { isLoggedIn } = this.authService;

    // If not logged in, redirect to login page
    if (isLoggedIn !== true) {
      this.router.navigate(['login']);
    }

    // If page role doesn't match the role, redirect to correct role page
    if (route.data['roles'].indexOf(userRole) === -1) {
      this.router.navigate([redirectURI]);
    }

    //Todo: Redirect in case of user changes their ID in url

    // If jwt token not expired
    if (accessToken && !this.jwtHelper.isTokenExpired(accessToken)) {
      return true;
    }

    const isRefreshSuccess = await this.refreshingTokens(accessToken, refreshToken);
    if (!isRefreshSuccess) {
      this.authService.logOut();

      this.notificationService.showError('Session expired. Please log in again.', 'Error')

      this.router.navigate(["login"]);
    }

    return isRefreshSuccess;
  }

  private async refreshingTokens(accessToken: string | null, refreshToken: string | null): Promise<boolean> {
    if (!accessToken || !refreshToken) {
      return false;
    }

    let isRefreshSuccess: boolean;

    try {
      const apiUrl: string = 'http://localhost:8080/api';
      const response = await lastValueFrom(this.http.get(apiUrl + "/token/refresh", {
        context: new HttpContext().set(BYPASS_ACCESS_TOKEN, true),
      }));

      this.authService.setSession(response);

      //! Experimantal
      this.notificationService.showSuccess("Token renewed successfully", "Success")

      isRefreshSuccess = true;
    }
    catch (ex) {
      isRefreshSuccess = false;
    }

    return isRefreshSuccess;
  }
}
