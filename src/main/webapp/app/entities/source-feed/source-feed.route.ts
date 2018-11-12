import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SourceFeed } from 'app/shared/model/source-feed.model';
import { SourceFeedService } from './source-feed.service';
import { SourceFeedComponent } from './source-feed.component';
import { SourceFeedDetailComponent } from './source-feed-detail.component';
import { SourceFeedUpdateComponent } from './source-feed-update.component';
import { SourceFeedDeletePopupComponent } from './source-feed-delete-dialog.component';
import { ISourceFeed } from 'app/shared/model/source-feed.model';

@Injectable({ providedIn: 'root' })
export class SourceFeedResolve implements Resolve<ISourceFeed> {
    constructor(private service: SourceFeedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sourceFeed: HttpResponse<SourceFeed>) => sourceFeed.body));
        }
        return of(new SourceFeed());
    }
}

export const sourceFeedRoute: Routes = [
    {
        path: 'source-feed',
        component: SourceFeedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed/search',
        component: SourceFeedComponent,
        resolve: {
            sourceFeed: SourceFeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed/:id/view',
        component: SourceFeedDetailComponent,
        resolve: {
            sourceFeed: SourceFeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed/new',
        component: SourceFeedUpdateComponent,
        resolve: {
            sourceFeed: SourceFeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed/:id/edit',
        component: SourceFeedUpdateComponent,
        resolve: {
            sourceFeed: SourceFeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceFeedPopupRoute: Routes = [
    {
        path: 'source-feed/:id/delete',
        component: SourceFeedDeletePopupComponent,
        resolve: {
            sourceFeed: SourceFeedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeeds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
