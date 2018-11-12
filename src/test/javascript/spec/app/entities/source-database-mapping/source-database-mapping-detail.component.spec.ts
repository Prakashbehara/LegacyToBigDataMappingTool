/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseMappingDetailComponent } from 'app/entities/source-database-mapping/source-database-mapping-detail.component';
import { SourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

describe('Component Tests', () => {
    describe('SourceDatabaseMapping Management Detail Component', () => {
        let comp: SourceDatabaseMappingDetailComponent;
        let fixture: ComponentFixture<SourceDatabaseMappingDetailComponent>;
        const route = ({ data: of({ sourceDatabaseMapping: new SourceDatabaseMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceDatabaseMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceDatabaseMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceDatabaseMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
