import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Application } from 'app/shared/model/application.model';
import { ApplicationService } from './application.service';
import { ApplicationComponent } from './application.component';
import { ApplicationDetailComponent } from './application-detail.component';
import { ApplicationUpdateComponent } from './application-update.component';
import { ApplicationDeletePopupComponent } from './application-delete-dialog.component';
import { IApplication } from 'app/shared/model/application.model';

@Injectable({ providedIn: 'root' })
export class ApplicationResolve implements Resolve<IApplication> {
    constructor(private service: ApplicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((application: HttpResponse<Application>) => application.body));
        }
        return of(new Application());
    }
}

export const applicationRoute: Routes = [
    {
        path: 'application',
        component: ApplicationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application/:id/view',
        component: ApplicationDetailComponent,
        resolve: {
            application: ApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application/new',
        component: ApplicationUpdateComponent,
        resolve: {
            application: ApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application/:id/edit',
        component: ApplicationUpdateComponent,
        resolve: {
            application: ApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationPopupRoute: Routes = [
    {
        path: 'application/:id/delete',
        component: ApplicationDeletePopupComponent,
        resolve: {
            application: ApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
