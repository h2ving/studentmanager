import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlSegment } from '@angular/router';
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

  canLoad() { }

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const isLoggedIn: boolean = this.authService.isLoggedIn;
    const { accessToken, refreshToken }: {
      accessToken: string, refreshToken: string
    } = this.authService.getTokens!;
    const { url }: { url: string } = state;

    const redirectURI: string | undefined = this.jwtHelper.decodeToken(accessToken).redirectURI;

    // If not logged in, redirect to login page
    if (isLoggedIn !== true) {
      this.router.navigate(['login']);
    }

    // Redirect back to Dashboard if User acces to another role/ID
    if (url !== redirectURI) {
      this.router.navigate([redirectURI]);
    }

    // If jwt token not expired
    if (accessToken && !this.jwtHelper.isTokenExpired(accessToken)) {
      return true;
    }

    const isRefreshSuccess = await this.refreshingTokens(accessToken, refreshToken);
    if (!isRefreshSuccess) {
      this.authService.logOut();

      this.notificationService.showError('Session expired. Please log in again.');
    }

    return isRefreshSuccess;
  }

  private async refreshingTokens(accessToken: string, refreshToken: string): Promise<boolean> {
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
      this.notificationService.showSuccess("Token renewed successfully");

      isRefreshSuccess = true;
    }
    catch (ex) {
      isRefreshSuccess = false;
    }

    return isRefreshSuccess;
  }
}
